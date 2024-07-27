package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class EventWithUserDto(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val users: List<UserDto>,
) {
    companion object {
        fun of(
            event: Event,
            users: List<User>,
        ): EventWithUserDto =
            EventWithUserDto(
                id = event.id,
                eventTitle = event.eventTitle,
                deadLine = event.deadLine,
                notificationStartTime = event.notificationStartTime,
                notificationInterval = event.notificationInterval,
                users = users.map { UserDto.of(it) },
            )
    }
}
