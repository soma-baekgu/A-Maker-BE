package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.domain.event.ReactionOption
import java.time.LocalDateTime

data class ReactionEventDetailDto(
    val id: Long,
    val eventTitle: String,
    val options: List<ReactionOptionDto>,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val eventCreator: UserDto,
    val finishUser: List<UserDto>,
    val waitingUser: List<UserDto>,
) {
    companion object {
        fun of(
            reactionEvent: ReactionEvent,
            reactionOptions: List<ReactionOption>,
            eventCreator: UserDto,
            finishUser: List<UserDto>,
            waitingUser: List<UserDto>,
        ) = ReactionEventDetailDto(
            id = reactionEvent.id,
            eventTitle = reactionEvent.eventTitle,
            options = reactionOptions.map { ReactionOptionDto.of(it) },
            deadLine = reactionEvent.deadLine,
            notificationStartTime = reactionEvent.notificationStartTime,
            notificationInterval = reactionEvent.notificationInterval,
            eventCreator = eventCreator,
            finishUser = finishUser,
            waitingUser = waitingUser,
        )
    }
}
