package com.backgu.amaker.infra.mail.service

import com.backgu.amaker.domain.notifiacation.Notification
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EmailEventService(
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun publishEmailEvent(emailEvent: Notification) {
        eventPublisher.publishEvent(emailEvent)
    }
}
