package br.com.userservice.core.User;

import br.com.userservice.application.dto.UserRequest;
import br.com.userservice.application.producer.SagaProducer;
import br.com.userservice.core.Event.EventService;
import br.com.userservice.core.document.Event;
import br.com.userservice.core.document.User;
import br.com.userservice.core.utils.JsonUtil;
import br.com.userservice.infrastructure.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final String TRANSACTION_ID_PATTERN = "%s_%s";

    private final EventService eventService;
    private final SagaProducer producer;
    private final JsonUtil jsonUtil;
    private final UserRepository repository;

    public User createUser(UserRequest userRequest) {
        var user =  User.builder()
                .transactionId(String.format(
                        TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID()
                        ))
                .name(userRequest.getName())
                .document(userRequest.getDocument())
                .cep(userRequest.getCep())
                .createdAt(LocalDateTime.now())
                .build();
        producer.sendEvent(jsonUtil.toJson(createPayload(repository.save(user))));
        return user;
    }

    private Event createPayload(User user) {
        var event = Event.builder()
                .userId(user.getId())
                .transactionId(user.getTransactionId())
                .payload(user)
                .createdAt(LocalDateTime.now())
                .build();

        eventService.save(event);
        return event;
    }
}
