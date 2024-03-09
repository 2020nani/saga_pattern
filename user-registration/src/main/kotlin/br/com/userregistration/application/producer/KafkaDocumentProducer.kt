package br.com.userregistration.application.producer

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaDocumentProducer (
    val kafkaTemplate: KafkaTemplate<String, Any>,

    @Value("\${spring.kafka.topic.orchestrator}")
    val orchestratorTopic: String
) {

    private val log = KotlinLogging.logger {}

    fun sendEvent(payload: String) {
        try {
            log.info("Sending event to topic {} with data {}", orchestratorTopic, payload)
            kafkaTemplate.send(orchestratorTopic, payload)
        } catch (ex: Exception) {
            log.error("Error trying to send data to topic {} with data {}", orchestratorTopic, payload, ex)
        }
    }
}