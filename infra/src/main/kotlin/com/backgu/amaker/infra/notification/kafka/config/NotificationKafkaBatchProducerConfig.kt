package com.backgu.amaker.infra.notification.kafka.config

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import com.backgu.amaker.infra.notification.kafka.service.KafkaNotificationEventCallbackService
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.HashMap

@Configuration
@Import(KafkaConfig::class)
class NotificationKafkaBatchProducerConfig(
    private val kafkaConfig: KafkaConfig,
) {
    @Bean
    fun producerConfig(): Map<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig.bootstrapServers
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "all"
        props[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true

        return props
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, NotificationEvent> = DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, NotificationEvent> = KafkaTemplate(producerFactory())

    @Bean
    fun notificationEventCallBackService(): NotificationEventService = KafkaNotificationEventCallbackService(kafkaTemplate())
}
