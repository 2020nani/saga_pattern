package br.com.userregistration.application.dto

import br.com.userregistration.core.enums.ESagaStatus
import org.springframework.util.ObjectUtils
import java.time.LocalDateTime


class Event {
    private val id: String? = null
    private val userId: String? = null
    private val transactionId: String? = null
    private val payload: User? = null
    private var source: String? = null
    private var status: ESagaStatus? = null
    private var eventHistory: MutableList<History>? = null
    private val createdAt: LocalDateTime? = null

    fun addToHistory(history: History) {
        if (ObjectUtils.isEmpty(eventHistory)) {
            eventHistory = ArrayList()
        }
        eventHistory!!.add(history)
    }

    fun setStatus(status: ESagaStatus) { this.status = status};
    fun setSource(source: String) { this.source = source}
    fun getId(): String? {return this.id}
    fun getUserId(): String? { return this.userId};
    fun getTransactionId(): String? { return this.transactionId};
    fun getPayload(): User? { return this.payload};
    fun getSource(): String? { return this.source};
    fun getStatus(): ESagaStatus? { return this.status};
    fun getEventHistory(): MutableList<History>? {return this.eventHistory};
    fun getCreatedAt(): LocalDateTime? {return this.createdAt}

    override fun toString(): String {
        return "Event(id=$id, userId=$userId, transactionId=$transactionId, payload=$payload, source=$source, status=$status, eventHistory=$eventHistory, createdAt=$createdAt)"
    };
}