package com.backgu.amaker.infra.notification.kafka.config

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import com.backgu.amaker.infra.notification.kafka.serializer.KafkaNotificationSerializer
import com.backgu.amaker.infra.notification.kafka.service.KafkaNotificationEventService
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.util.HashMap

@Configuration
@Import(KafkaConfig::class)
class NotificationKafkaProducerConfig(
    private val kafkaConfig: KafkaConfig,
) {
    @Bean
    fun producerConfig(): Map<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig.bootstrapServers
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaNotificationSerializer::class.java
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "all"
        props[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true

        return props
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, Notification> = DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Notification> = KafkaTemplate(producerFactory())

    @Bean
    fun kafkaNotificationEventService(): NotificationEventService = KafkaNotificationEventService(kafkaTemplate())
}
