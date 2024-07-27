package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.ReplyEvent
import java.time.LocalDateTime

data class ReplyEventDetailDto(
    val id: Long,
    val eventTitle: String,
    val eventDetails: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val eventCreator: UserDto,
    val finishUser: List<UserDto>,
    val waitingUser: List<UserDto>,
) {
    companion object {
        fun of(
            replyEvent: ReplyEvent,
            eventCreator: UserDto,
            finishUser: List<UserDto>,
            waitingUser: List<UserDto>,
        ) = ReplyEventDetailDto(
            id = replyEvent.id,
            eventTitle = replyEvent.eventTitle,
            eventDetails = replyEvent.eventDetails,
            deadLine = replyEvent.deadLine,
            notificationStartTime = replyEvent.notificationStartTime,
            notificationInterval = replyEvent.notificationInterval,
            eventCreator = eventCreator,
            finishUser = finishUser,
            waitingUser = waitingUser,
        )
    }
}
