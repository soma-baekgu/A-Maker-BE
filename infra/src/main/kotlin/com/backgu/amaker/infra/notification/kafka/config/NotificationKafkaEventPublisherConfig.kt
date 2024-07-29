package com.backgu.amaker.infra.notification.kafka.config

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import com.backgu.amaker.infra.notification.kafka.deserializer.KafkaNotificationDeserializer
import com.backgu.amaker.infra.notification.kafka.service.KafkaEventPublisher
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties
import java.util.HashMap

@Configuration
@Import(KafkaConfig::class)
class NotificationKafkaEventPublisherConfig(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val kafkaConfig: KafkaConfig,
) {
    @Bean
    fun kafkaNotificationFacadeService(): KafkaEventPublisher = KafkaEventPublisher(applicationEventPublisher)

    @Bean
    fun consumerConfig(): Map<String, Any> {
        val props: HashMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig.bootstrapServers
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaNotificationDeserializer::class.java
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false

        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, NotificationEvent> = DefaultKafkaConsumerFactory(consumerConfig())

    @Bean
    fun notificationContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NotificationEvent>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, NotificationEvent>()
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }
}
