package br.com.adressservice.core.service;

import br.com.adressservice.application.dto.AdressDto;
import br.com.adressservice.application.dto.Event;
import br.com.adressservice.application.dto.History;
import br.com.adressservice.application.exception.ValidationException;
import br.com.adressservice.application.integration.graphql.adress.AdressClient;
import br.com.adressservice.application.producer.KafkaProducer;
import br.com.adressservice.core.model.Adress;
import br.com.adressservice.core.model.Validation;
import br.com.adressservice.core.utils.JsonUtil;
import br.com.adressservice.infrastructure.repository.AdressRepository;
import br.com.adressservice.infrastructure.repository.ValidationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static br.com.adressservice.core.enums.ESagaStatus.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class AdressValidationService {

    private static final String CURRENT_SOURCE = "ADRESS_VALIDATION_SERVICE";

    private final JsonUtil jsonUtil;
    private final KafkaProducer producer;
    private final AdressRepository adressRepository;
    private final ValidationRepository validationRepository;
    private final AdressClient adressClient;

    public void validateExistingadresss(Event event) {
        try {
            checkCurrentValidation(event);
            createValidation(event, true);
            handleSuccess(event);
        } catch (Exception ex) {
            log.error("Error trying to found adress: ", ex);
            handleFailCurrentNotExecuted(event, ex.getMessage());
        }
        producer.sendEvent(jsonUtil.toJson(event));
    }

    private void checkCurrentValidation(Event event) {
        validatedEvent(event);
        if (validationRepository.existsByUserIdAndTransactionId(
                event.getUserId(), event.getTransactionId())) {
            throw new ValidationException("There's another transactionId for this validation.");
        }

    }

    private void validatedEvent(Event event) {
        if (isEmpty(event.getPayload()) || isEmpty(event.getPayload().getCep())){
            throw new ValidationException("User or cep is empty!");
        }
        if (isEmpty(event.getPayload().getId()) || isEmpty(event.getTransactionId())) {
            throw new ValidationException("UserID and TransactionID must be informed!");
        }
    }

    private void createValidation(Event event, boolean success) {
        var validation = Validation
                .builder()
                .userId(event.getPayload().getId())
                .transactionId(event.getTransactionId())
                .success(success)
                .build();
        validationRepository.save(validation);
    }

    private void handleSuccess(Event event) throws IOException {

        AdressDto adressDto = adressClient.getAdressDetails(event.getPayload().getCep());
        event.getPayload().setAdress(
                adressRepository.save(adressDto.getData().getAdress().mapperAdress())
        );
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);
        log.info("Event {}", event);
        addHistory(event, "adress was validated successfully! {}");
    }

    private void addHistory(Event event, String message) {
        var history = History
            .builder()
            .source(event.getSource())
            .status(event.getStatus())
            .message(message)
            .createdAt(LocalDateTime.now())
            .build();
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Fail to validate adress: ".concat(message));
    }

    public void rollbackEvent(Event event) {
        changeValidationToFail(event);
        event.setStatus(FAIL);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Rollback executed on adress validation!");
        producer.sendEvent(jsonUtil.toJson(event));
    }

    private void changeValidationToFail(Event event) {
        validationRepository
            .findByUserIdAndTransactionId(event.getUserId(), event.getTransactionId())
            .ifPresentOrElse(validation -> {
                    validation.setSuccess(false);
                    validationRepository.save(validation);
                },
                () -> createValidation(event, false));
    }
}
