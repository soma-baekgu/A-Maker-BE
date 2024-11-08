package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.TaskEvent
import java.time.LocalDateTime

data class TaskEventDetailDto(
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
            taskEvent: TaskEvent,
            eventCreator: UserDto,
            finishUser: List<UserDto>,
            waitingUser: List<UserDto>,
        ) = TaskEventDetailDto(
            id = taskEvent.id,
            eventTitle = taskEvent.eventTitle,
            eventDetails = taskEvent.eventDetails,
            deadLine = taskEvent.deadLine,
            notificationStartTime = taskEvent.notificationStartTime,
            notificationInterval = taskEvent.notificationInterval,
            eventCreator = eventCreator,
            finishUser = finishUser,
            waitingUser = waitingUser,
        )
    }
}
