package com.backgu.amaker.api.notification.config

import com.backgu.amaker.application.notification.service.NotificationQueryService
import com.backgu.amaker.infra.jpa.notification.repository.EventNotificationRepository
import com.backgu.amaker.infra.notification.kafka.config.NotificationKafkaProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(NotificationKafkaProducerConfig::class)
class NotificationConfig {
    @Bean
    fun notificationQueryService(notificationRepository: EventNotificationRepository): NotificationQueryService =
        NotificationQueryService(notificationRepository)
}
