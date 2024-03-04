package br.com.validateddocumentservice.core.service

import br.com.validateddocumentservice.application.dto.Event
import br.com.validateddocumentservice.application.dto.History
import br.com.validateddocumentservice.application.exception.ValidationException
import br.com.validateddocumentservice.application.integration.graphql.document.DocumentClient
import br.com.validateddocumentservice.application.producer.KafkaDocumentProducer
import br.com.validateddocumentservice.core.enums.ESagaStatus.*
import br.com.validateddocumentservice.core.model.Validation
import br.com.validateddocumentservice.core.utils.JsonUtil
import br.com.validateddocumentservice.infrastructure.repository.ValidationRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils.isEmpty
import java.io.IOException
import java.time.LocalDateTime
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
class DocumentValidateService (
    val jsonUtil: JsonUtil,
    val producer: KafkaDocumentProducer,
    private val validationRepository: ValidationRepository,
    private val documentClient: DocumentClient
) {
    private val CURRENT_SOURCE = "VALIDATED_SERVICE"

    private val log = KotlinLogging.logger {}

    fun validateDocument(event: Event) {
        try {
            println("teste")
            println(documentClient.getDocument("33509878976"))
            checkCurrentValidation(event)
            createValidation(event, true)
            handleSuccess(event)
        } catch (ex: Exception) {
            log.error("Error trying to found adress: ", ex)
            handleFailCurrentNotExecuted(event, ex.message)
        }
        producer.sendEvent(jsonUtil.toJson(event))
    }

    private fun checkCurrentValidation(event: Event): Boolean {
        var existValidation = validationRepository.existsByUserIdAndTransactionId(
            event.getUserId()!!, event.getTransactionId()!!
        )

        if (existValidation) {
            throw ValidationException("There's another transactionId for this validation.")
        }

        return existValidation;
    }

    private fun validatedEvent(event: Event) {
        if (isEmpty(event.getPayload()) || isEmpty(event.getPayload()?.getCep())) {
            throw ValidationException("User or cep is empty!")
        }
        if (isEmpty(event.getPayload()?.getId()) || isEmpty(event.getTransactionId())) {
            throw ValidationException("UserID and TransactionID must be informed!")
        }
    }

    private fun createValidation(event: Event, success: Boolean) {
        val validation = Validation(
            event.getPayload()!!.getId()!!,event.getTransactionId()!!,success
        );

        validationRepository.save(validation)
    }

    @Throws(IOException::class)
    private fun handleSuccess(event: Event) {

        event.setStatus(SUCCESS)
        event.setSource(CURRENT_SOURCE)
        log.info("Event {}", event)
        addHistory(event, "adress are validated successfully! {}")
    }

    private fun addHistory(event: Event, message: String) {
        val history = History(event.getSource()!!,event.getStatus()!!,message,LocalDateTime.now())

        event.addToHistory(history)
    }

    private fun handleFailCurrentNotExecuted(event: Event, message: String?) {
        event.setStatus(ROLLBACK_PENDING)
        event.setSource(CURRENT_SOURCE)
        addHistory(event, "Fail to validate adress: $message")
    }

    fun rollbackEvent(event: Event) {
        changeValidationToFail(event)
        event.setStatus(FAIL)
        event.setSource(CURRENT_SOURCE)
        addHistory(event, "Rollback executed on adress validation!")
        producer.sendEvent(jsonUtil.toJson(event))
    }

    private fun changeValidationToFail(event: Event) {
        var validation: Validation = validationRepository
            .findByUserIdAndTransactionId(event.getUserId()!!, event.getTransactionId()!!)
            .getOrElse { throw ValidationException("There is not validation") }
        validation.setSuccess(false)
        validationRepository.save(validation)
        createValidation(event, false)
    }
}