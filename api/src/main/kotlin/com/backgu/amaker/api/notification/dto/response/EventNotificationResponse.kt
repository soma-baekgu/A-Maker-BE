package com.backgu.amaker.api.notification.dto.response

import com.backgu.amaker.api.notification.dto.EventNotificationDto
import io.swagger.v3.oas.annotations.media.Schema

data class EventNotificationResponse(
    @Schema(description = "이벤트 id", example = "1")
    val id: Long,
    @Schema(description = "이벤트 제목", example = "이벤트 제목")
    val title: String,
    @Schema(description = "이벤트 내용", example = "이벤트 내용")
    val content: String,
    @Schema(description = "유저 id", example = "2")
    val userId: String,
    @Schema(description = "이벤트 id", example = "2")
    val eventId: Long,
) {
    companion object {
        fun of(eventNotification: EventNotificationDto) =
            EventNotificationResponse(
                id = eventNotification.id,
                title = eventNotification.title,
                content = eventNotification.content,
                userId = eventNotification.userId,
                eventId = eventNotification.eventId,
            )
    }
}
