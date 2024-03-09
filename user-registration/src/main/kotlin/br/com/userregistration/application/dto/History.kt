package br.com.userregistration.application.dto

import br.com.userregistration.core.enums.ESagaStatus
import java.time.LocalDateTime

class History (
    private val source: String,
    private val status: ESagaStatus,
    private val message: String,
    private val createdAt: LocalDateTime?
){
    fun getSource(): String {return this.source}
    fun getStatus(): ESagaStatus {return this.status}
    fun getMessage(): String {return this.message}
    fun getCreatedAt(): LocalDateTime? {return this.createdAt}
}