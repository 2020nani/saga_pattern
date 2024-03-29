package br.com.validateddocumentservice.infrastructure.repository

import br.com.validateddocumentservice.core.model.Validation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
    interface ValidationRepository : JpaRepository<Validation, Int> {
    fun existsByUserIdAndTransactionId(userId: String, transactionId: String): Boolean
    fun findByTransactionId(transactionId: String): Optional<Validation>
}