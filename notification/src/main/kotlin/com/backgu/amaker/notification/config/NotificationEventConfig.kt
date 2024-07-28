package com.backgu.amaker.notification.config

import com.backgu.amaker.infra.notification.kafka.config.NotificationKafkaEventPublisherConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(NotificationKafkaEventPublisherConfig::class)
class NotificationEventConfig
