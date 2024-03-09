package br.com.userregistration.core.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user-register")
class UserRegister (
            private val name: String,
            private val document: String,
            private val cep: String,
            @OneToOne
            private val adress: AdressRegister,
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
}