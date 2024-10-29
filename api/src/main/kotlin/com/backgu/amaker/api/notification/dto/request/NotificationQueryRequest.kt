package com.backgu.amaker.api.notification.dto.request

import com.backgu.amaker.api.notification.dto.NotificationQueryDto
import io.swagger.v3.oas.annotations.media.Schema

data class NotificationQueryRequest(
    @Schema(description = "읽어올 페이지 번호", example = "2", defaultValue = "0")
    val page: Int = 0,
    @Schema(description = "읽어올 응답의 개수", example = "100", defaultValue = "20")
    val size: Int = 20,
) {
    fun toDto(notificationId: Long): NotificationQueryDto =
        NotificationQueryDto(
            notificationId = notificationId,
            size = size,
        )
}
