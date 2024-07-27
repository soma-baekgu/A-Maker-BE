package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.EventWithUserDto
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class EventWithUserResponse(
    @Schema(
        description = "이벤트 제목",
        example = "우리 어디서 만날지",
    )
    val eventTitle: String,
    @Schema(
        description = "데드라인",
        example = "2024-07-24T07:39:37.598",
    )
    val deadLine: LocalDateTime,
    @Schema(
        description = "알림 보낼 시작 시간",
        example = "2024-07-24T06:09:37.598",
    )
    val notificationStartTime: LocalDateTime,
    @Schema(
        description = "알림 주기",
        example = "15",
    )
    val notificationInterval: Int,
    @Schema(
        description = "유저 사진 리스트",
        example = "[\"http://a-maker.com/hi1.png\", \"http://a-maker.com/hi2.png\"]",
    )
    val users: List<String>,
) {
    companion object {
        fun of(event: EventWithUserDto) =
            EventWithUserResponse(
                eventTitle = event.eventTitle,
                deadLine = event.deadLine,
                notificationStartTime = event.notificationStartTime,
                notificationInterval = event.notificationInterval,
                users = event.users.map { it.picture },
            )
    }
}
