package com.backgu.amaker.infra.mail.service

import com.backgu.amaker.application.notification.mail.event.EmailEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EmailEventService(
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun publishEmailEvent(emailEvent: EmailEvent) {
        eventPublisher.publishEvent(emailEvent)
    }
}
