package com.backgu.amaker.domain.notifiacation.event

import com.backgu.amaker.domain.notifiacation.UserNotification
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.domain.notifiacation.type.UserNotificationType
import java.time.Duration

class EventOverdue private constructor(
    userId: String,
    method: TemplateEmailNotificationMethod,
) : UserNotification(
        userId,
        method,
    ) {
    companion object {
        private fun buildDetailMessage(): String = "마감이 지난 이벤트입니다."

        private fun getFormattedMessage(
            eventTitle: String,
            eventMessage: String,
            duration: Duration,
        ): String {
            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60
            val timeLeft = if (hours > 0) "${hours}시간 ${minutes}분" else "${minutes}분"
            return String.format(eventMessage, eventTitle, timeLeft)
        }

        fun of(
            userId: String,
            duration: Duration,
        ): EventOverdue =
            EventOverdue(
                userId,
                TemplateEmailNotificationMethod(
                    UserNotificationType.OVERDUE.title,
                    getFormattedMessage(
                        UserNotificationType.OVERDUE.title,
                        UserNotificationType.OVERDUE.message,
                        duration,
                    ),
                    buildDetailMessage(),
                    "event-notification",
                ),
            )
    }
}
