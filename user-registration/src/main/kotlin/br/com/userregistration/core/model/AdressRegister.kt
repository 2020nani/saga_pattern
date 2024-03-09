package br.com.userregistration.core.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "adress-register")
class AdressRegister (
    private val code: String,
    private val street: String,
    private val number: String,
    private val district: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    @OneToOne
    private val userRegister: UserRegister? = null

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