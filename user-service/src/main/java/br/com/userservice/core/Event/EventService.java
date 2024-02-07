package br.com.userservice.core.Event;

import br.com.userservice.application.dto.EventFilters;
import br.com.userservice.application.exception.ValidationException;
import br.com.userservice.core.document.Event;
import br.com.userservice.infrastructure.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository repository;

    public void notifyEnding(Event event) {
        Event endEvent = new Event(
                event.getId(),
                event.getUserId(),
                event.getTransactionId(),
                event.getPayload(),
                event.getSource(),
                event.getStatus(),
                event.getEventHistory(),
                LocalDateTime.now()
        );
        save(endEvent);
        log.info("User {} with saga notified! TransactionId: {}", event.getId(), event.getTransactionId());
    }

    public List<Event> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public Event findByFilters(EventFilters filters) {
        validateEmptyFilters(filters);
        if (!isEmpty(filters.getUserId())) {
            return findByOrderId(filters.getUserId());
        } else {
            return findByTransactionId(filters.getTransactionId());
        }
    }

    private void validateEmptyFilters(EventFilters filters) {
        if (isEmpty(filters.getUserId()) && isEmpty(filters.getTransactionId())) {
            throw new ValidationException("Document or TransactionID must be informed.");
        }
    }

    private Event findByTransactionId(String transactionId) {
        return repository
            .findTop1ByTransactionIdOrderByCreatedAtDesc(transactionId)
            .orElseThrow(() -> new ValidationException("Event not found by transactionId."));
    }

    private Event findByOrderId(String userId) {
        return repository
            .findTop1ByUserIdOrderByCreatedAtDesc(userId)
            .orElseThrow(() -> new ValidationException("Event not found by userId."));
    }

    public Event save(Event event) {
        return repository.save(event);
    }
}
