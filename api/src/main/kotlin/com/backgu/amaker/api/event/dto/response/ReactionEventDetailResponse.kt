package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.ReactionEventDetailDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ReactionEventDetailResponse(
    @Schema(description = "이벤트 id", example = "1")
    val id: Long,
    @Schema(description = "이벤트 제목", example = "우리 어디서 만날지")
    val eventTitle: String,
    @Schema(description = "선택지")
    val options: List<ReactionOptionResponse>,
    @Schema(description = "데드라인", example = "2024-07-24T07:39:37.598")
    val deadLine: LocalDateTime,
    @Schema(description = "알림 보낼 시작 시간", example = "2024-07-24T06:09:37.598")
    val notificationStartTime: LocalDateTime,
    @Schema(description = "알림 주기", example = "15")
    val notificationInterval: Int,
    @Schema(description = "이벤트 생성자")
    val eventCreator: UserResponse,
    @Schema(description = "이벤트를 수행한 유저")
    val finishUser: List<UserResponse>,
    @Schema(description = "이벤트 수행 대기중인 유저")
    val waitingUser: List<UserResponse>,
) {
    companion object {
        fun of(reactionEventDetailDto: ReactionEventDetailDto) =
            ReactionEventDetailResponse(
                id = reactionEventDetailDto.id,
                eventTitle = reactionEventDetailDto.eventTitle,
                options = reactionEventDetailDto.options.map { ReactionOptionResponse.of(it) },
                deadLine = reactionEventDetailDto.deadLine,
                notificationStartTime = reactionEventDetailDto.notificationStartTime,
                notificationInterval = reactionEventDetailDto.notificationInterval,
                eventCreator = UserResponse.of(reactionEventDetailDto.eventCreator),
                finishUser = reactionEventDetailDto.finishUser.map { UserResponse.of(it) },
                waitingUser = reactionEventDetailDto.waitingUser.map { UserResponse.of(it) },
            )
    }
}
