package com.backgu.amaker.event.dto

import com.backgu.amaker.event.domain.Event
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserDto
import java.time.LocalDateTime

class EventDtoWithUser(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val eventDetails: String,
    val users: List<UserDto>,
) {
    companion object {
        fun of(
            event: Event,
            users: List<User>,
        ): EventDtoWithUser =
            EventDtoWithUser(
                id = event.id,
                eventTitle = event.eventTitle,
                deadLine = event.deadLine,
                notificationStartTime = event.notificationStartTime,
                notificationInterval = event.notificationInterval,
                eventDetails = event.eventDetails,
                users = users.map { UserDto.of(it) },
            )
    }
}
