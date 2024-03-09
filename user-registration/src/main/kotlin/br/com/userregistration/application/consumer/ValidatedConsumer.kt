package br.com.userregistration.application.consumer

import br.com.userregistration.application.dto.Event
import br.com.userregistration.core.service.UserRegistrationService
import br.com.userregistration.core.utils.JsonUtil
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ValidatedConsumer (
    val userRegistrationService: UserRegistrationService,
    val jsonUtil: JsonUtil
) {
    private val log = KotlinLogging.logger {}

    @KafkaListener(
            groupId = "\${spring.kafka.consumer.group-id}",
            topics = ["\${spring.kafka.topic.finish-success}"]
    )
    fun consumeSuccessEvent(payload: String?) {
        log.info("Receiving success event {} from finish-success topic", payload)
        val event: Event? = jsonUtil.toEvent(payload)
        userRegistrationService.validateDocument(event!!);
    }

    @KafkaListener(
            groupId = "\${spring.kafka.consumer.group-id}",
            topics = ["\${spring.kafka.topic.registration-fail}"]
    )
    fun consumeFailEvent(payload: String?) {
        log.info("Receiving rollback event {} from registration-fail topic", payload)
        val event: Event? = jsonUtil.toEvent(payload)
        userRegistrationService.rollbackEvent(event!!)
    }
}