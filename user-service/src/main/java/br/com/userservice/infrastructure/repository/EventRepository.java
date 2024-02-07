package br.com.userservice.infrastructure.repository;

import br.com.userservice.core.document.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findAllByOrderByCreatedAtDesc();

    Optional<Event> findTop1ByUserIdOrderByCreatedAtDesc(String userId);

    Optional<Event> findTop1ByTransactionIdOrderByCreatedAtDesc(String transactionId);
}
