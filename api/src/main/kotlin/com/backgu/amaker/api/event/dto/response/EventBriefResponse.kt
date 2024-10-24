package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.EventWithUserAndChatRoomDto
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class EventBriefResponse(
    @Schema(
        description = "이벤트 타입",
        example = "TASK",
    )
    val eventType: String,
    @Schema(
        description = "이벤트 아이디",
        example = "2",
    )
    val eventId: Long,
    @Schema(
        description = "채팅방 아이디",
        example = "21",
    )
    val chatRoomId: Long,
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
    @Schema(
        description = "완료된 이벤트 수",
        example = "2",
    )
    val finishedCount: Int,
    @Schema(
        description = "총 배정된 이벤트 수",
        example = "5",
    )
    val totalAssignedCount: Int,
    @Schema(
        description = "이벤트가 자신의 것인지",
        example = "true",
    )
    val isMine: Boolean,
) {
    companion object {
        fun of(
            event: EventWithUserAndChatRoomDto,
            userId: String,
        ) = EventBriefResponse(
            eventId = event.id,
            chatRoomId = event.chatRoomId,
            eventTitle = event.eventTitle,
            deadLine = event.deadLine,
            notificationStartTime = event.notificationStartTime,
            notificationInterval = event.notificationInterval,
            users = event.users.map { it.picture },
            finishedCount = event.finishedCount,
            totalAssignedCount = event.totalAssignedCount,
            isMine = event.users.any { it.id == userId },
            eventType = event.eventType,
        )
    }
}
