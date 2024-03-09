package br.com.userregistration.infrastructure.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.*
import org.springframework.stereotype.Component

@EnableKafka
@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    val bootstrapServers: String,

    @Value("\${spring.kafka.consumer.group-id}")
    val groupId: String,

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    val autoOffsetReset: String,

    @Value("\${spring.kafka.topic.orchestrator}")
    val orchestratorTopic: String,

    @Value("\${spring.kafka.topic.finish-success}")
    val validatedSuccessTopic: String,

    @Value("\${spring.kafka.topic.registration-fail}")
    val validatedFailTopic: String,
) {
    val PARTITION_COUNT = 1
    val REPLICA_COUNT = 1

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, String?>? {
        return DefaultKafkaConsumerFactory(consumerProps())
    }

    fun consumerProps(): Map<String, Any?> {
        val props = HashMap<String, Any?>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = autoOffsetReset
        return props
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, Any> {
        return DefaultKafkaProducerFactory(producerProps()!!)
    }

    fun producerProps(): Map<String, Any?>? {
        val props = HashMap<String, Any?>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return props
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    fun buildTopic(name: String?): NewTopic? {
        return TopicBuilder
            .name(name!!)
            .partitions(PARTITION_COUNT)
            .replicas(REPLICA_COUNT)
            .build()
    }

    @Bean
    fun orchestratorTopic(): NewTopic? {
        return buildTopic(orchestratorTopic)
    }

    @Bean
    fun validatedSuccessTopic(): NewTopic? {
        return buildTopic(validatedSuccessTopic)
    }

    @Bean
    fun validatedFailTopic(): NewTopic? {
        return buildTopic(validatedFailTopic)
    }
}