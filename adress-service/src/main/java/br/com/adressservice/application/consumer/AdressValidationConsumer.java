package br.com.adressservice.application.consumer;

import br.com.adressservice.core.service.AdressValidationService;
import br.com.adressservice.core.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdressValidationConsumer {

    private final AdressValidationService adressValidationService;
    private final JsonUtil jsonUtil;

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.adress-validation-success}"
    )
    public void consumeSuccessEvent(String payload) {
        log.info("Receiving success event {} from adress-validation-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        adressValidationService.validateExistingadresss(event);
    }

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.adress-validation-fail}"
    )
    public void consumeFailEvent(String payload) {
        log.info("Receiving rollback event {} from adress-validation-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        adressValidationService.rollbackEvent(event);
    }
}
