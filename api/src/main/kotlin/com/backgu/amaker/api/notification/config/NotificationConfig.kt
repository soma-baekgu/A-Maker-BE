package com.backgu.amaker.api.notification.config

import com.backgu.amaker.infra.notification.kafka.config.NotificationKafkaProducerConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(NotificationKafkaProducerConfig::class)
class NotificationConfig
