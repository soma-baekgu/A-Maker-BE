package com.backgu.amaker.domain.notifiacation.event

import com.backgu.amaker.domain.notifiacation.UserNotification
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.domain.notifiacation.type.UserNotificationType.NOT_COMPLETED
import java.time.Duration

class EventNotCompleted private constructor(
    userId: String,
    method: TemplateEmailNotificationMethod,
) : UserNotification(
        userId,
        method,
    ) {
    companion object {
        private fun buildDetailMessage(): String = "이벤트가 완료되지 않았습니다."

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
        ): EventNotCompleted =
            EventNotCompleted(
                userId,
                TemplateEmailNotificationMethod(
                    NOT_COMPLETED.title,
                    getFormattedMessage(NOT_COMPLETED.title, NOT_COMPLETED.message, duration),
                    buildDetailMessage(),
                    "event-notification",
                ),
            )
    }
}
