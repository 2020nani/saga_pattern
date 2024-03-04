package br.com.validateddocumentservice.application.consumer

import br.com.validateddocumentservice.application.dto.Event
import br.com.validateddocumentservice.core.service.DocumentValidateService
import br.com.validateddocumentservice.core.utils.JsonUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Slf4j
@Component
@RequiredArgsConstructor
class ValidatedConsumer (
    val documentValidateService: DocumentValidateService,
    val jsonUtil: JsonUtil
) {
    private val log = KotlinLogging.logger {}

    @KafkaListener(
            groupId = "\${spring.kafka.consumer.group-id}",
            topics = ["\${spring.kafka.topic.validated-success}"]
    )
    fun consumeSuccessEvent(payload: String?) {
        log.info("Receiving success event {} from validated-success topic", payload)
        val event: Event? = jsonUtil.toEvent(payload)
        documentValidateService.validateDocument(event!!);
    }

    @KafkaListener(
            groupId = "\${spring.kafka.consumer.group-id}",
            topics = ["\${spring.kafka.topic.validated-fail}"]
    )
    fun consumeFailEvent(payload: String?) {
        log.info("Receiving rollback event {} from validated-fail topic", payload)
        val event: Event? = jsonUtil.toEvent(payload)
        documentValidateService.rollbackEvent(event!!)
    }
}