package com.backgu.amaker.batch.config

import com.backgu.amaker.infra.notification.kafka.config.NotificationKafkaBatchProducerConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(NotificationKafkaBatchProducerConfig::class)
class NotificationConfig
