package br.com.userregistration.core.service

import br.com.userregistration.application.dto.Event
import br.com.userregistration.application.dto.History
import br.com.userregistration.application.exception.ValidationException
import br.com.userregistration.application.producer.KafkaDocumentProducer
import br.com.userregistration.core.enums.ESagaStatus
import br.com.userregistration.core.model.Validation
import br.com.userregistration.core.utils.JsonUtil
import br.com.userregistration.infrastructure.repository.ValidationRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils.isEmpty
import java.io.IOException
import br.com.userregistration.core.model.AdressRegister
import br.com.userregistration.core.model.UserRegister
import br.com.userregistration.infrastructure.repository.AdressRepository
import br.com.userregistration.infrastructure.repository.UserRegisterRepository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrElse

@Service
class UserRegistrationService (
    val jsonUtil: JsonUtil,
    val producer: KafkaDocumentProducer,
    private val validationRepository: ValidationRepository,
    private val userRegisterRepository: UserRegisterRepository,
    private val adressRepository: AdressRepository
) {
    private val CURRENT_SOURCE = "REGISTRATION_SERVICE"

    private val log = KotlinLogging.logger {}

    fun validateDocument(event: Event) {
        try {
            checkCurrentValidation(event)
            createValidation(event, true)
            validatedEvent(event)
            val adressRegister: AdressRegister = adressRepository.save(
                AdressRegister(
                    event.getPayload()!!.getAdress()!!.getCode()!!,
                    event.getPayload()!!.getAdress()!!.getStreet()!!,
                    event.getPayload()!!.getAdress()!!.getNumber()!!,
                    event.getPayload()!!.getAdress()!!.getDistrict()!!
                )
            )
            userRegisterRepository.save(UserRegister(
                event.getPayload()!!.getName()!!,
                event.getPayload()!!.getDocument()!!,
                event.getPayload()!!.getCep()!!,
                adressRegister
            ))
            handleSuccess(event)
        } catch (ex: Exception) {
            log.error("Error to persist user: ", ex)
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

        event.setStatus(ESagaStatus.SUCCESS)
        event.setSource(CURRENT_SOURCE)
        log.info("Event {}", event)
        addHistory(event, "user persisted with successfully! {}")
    }

    private fun addHistory(event: Event, message: String) {
        val history = History(event.getSource()!!,event.getStatus()!!,message, LocalDateTime.now())

        event.addToHistory(history)
    }

    private fun handleFailCurrentNotExecuted(event: Event, message: String?) {
        event.setStatus(ESagaStatus.ROLLBACK_PENDING)
        event.setSource(CURRENT_SOURCE)
        addHistory(event, "Fail to persist user: $message")
    }

    fun rollbackEvent(event: Event) {
        changeValidationToFail(event)
        event.setStatus(ESagaStatus.FAIL)
        event.setSource(CURRENT_SOURCE)
        addHistory(event, "Rollback executed on user registration!")
        producer.sendEvent(jsonUtil.toJson(event))
    }

    private fun changeValidationToFail(event: Event) {
        var validation: Validation = validationRepository
            .findByUserIdAndTransactionId(event.getUserId()!!,event.getTransactionId()!!)
            .getOrElse { throw ValidationException("There is not validation") }
        validation.setSuccess(false)

        validationRepository.save(validation)
        createValidation(event, false)
    }
}