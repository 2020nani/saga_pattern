package br.com.validateddocumentservice.core.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime


@Entity
@Table(name = "validation")
class Validation (
    @Column(nullable = false)
    private val userId: String,

    @Column(nullable = false)
    private val transactionId: String,

    @Column(nullable = false)
    private var success : Boolean,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    @Column(nullable = false, updatable = false)
    private var createdAt: LocalDateTime? = null

    @Column(nullable = false)
    private var updatedAt: LocalDateTime? = null

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }

    fun setSuccess(value: Boolean){ this.success = value};

    fun getUserId(): String {return this.userId};
    fun getTransactionId(): String {return this.transactionId}
    fun getSuccess(): Boolean {return this.success}

}

