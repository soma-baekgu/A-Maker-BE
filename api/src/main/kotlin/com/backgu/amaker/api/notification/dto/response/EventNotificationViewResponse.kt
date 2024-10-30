package com.backgu.amaker.api.notification.dto.response

import com.backgu.amaker.api.notification.dto.EventNotificationDto
import com.backgu.amaker.common.http.response.PageResponse
import org.springframework.data.domain.Page

class EventNotificationViewResponse(
    override val content: List<EventNotificationResponse>,
    override val pageNumber: Int,
    override val pageSize: Int,
    override val totalElements: Long,
    override val totalPages: Int,
    override val hasNext: Boolean,
    override val hasPrevious: Boolean,
    override val isFirst: Boolean,
    override val isLast: Boolean,
) : PageResponse<EventNotificationResponse> {
    companion object {
        fun of(eventNotifications: Page<EventNotificationDto>): EventNotificationViewResponse {
            val content = eventNotifications.content.map { EventNotificationResponse.of(it) }
            return EventNotificationViewResponse(
                content = content,
                pageNumber = eventNotifications.number,
                pageSize = eventNotifications.size,
                totalElements = eventNotifications.totalElements,
                totalPages = eventNotifications.totalPages,
                hasNext = eventNotifications.hasNext(),
                hasPrevious = eventNotifications.hasPrevious(),
                isFirst = eventNotifications.isFirst,
                isLast = eventNotifications.isLast,
            )
        }
    }
}
