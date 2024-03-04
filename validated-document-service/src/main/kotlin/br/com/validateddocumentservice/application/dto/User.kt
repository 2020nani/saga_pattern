package br.com.validateddocumentservice.application.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime

class User {
    private val id: String? = null
    private val transactionId: String? = null
    private val name: String? = null
    private val document: String? = null
    private val cep: String? = null
    private val adress: Adress? = null
    private val createdAt: LocalDateTime? = null

    fun getId(): String? {return this.id};
    fun getTransactionId(): String? {return this.transactionId};
    fun getDocument(): String? {return this.document};
    fun getCep(): String? {return this.cep}
    fun getName(): String? {return this.name}
    fun getAdress(): Adress? {return this.adress}
    fun getCreatedAt(): LocalDateTime? {return this.createdAt}

    override fun toString(): String {
        return "User(id=$id, transactionId=$transactionId, name=$name, document=$document, cep=$cep, adress=$adress, createdAt=$createdAt)"
    };
}