package com.backgu.amaker.batch.dto

import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.domain.notifiacation.EventNotificationType
import com.backgu.amaker.infra.mail.constants.EmailConstants

class EventNotificationEvent(
    email: String,
    title: String,
    content: String,
    notificationType: EventNotificationType,
) : EmailEvent(
        email = email,
        emailConstants =
            if (notificationType ==
                EventNotificationType.NOT_COMPLETED
            ) {
                EmailConstants.NOT_COMPLETED_NOTIFICATION
            } else {
                EmailConstants.OVERDUE_NOTIFICATION
            },
        title = title,
        content = content,
    )
