package br.com.adressservice.infrastructure.repository;

import br.com.adressservice.core.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Boolean existsByUserIdAndTransactionId(String userId, String transactionId);
    Optional<Validation> findByUserIdAndTransactionId(String userId, String transactionId);
}
