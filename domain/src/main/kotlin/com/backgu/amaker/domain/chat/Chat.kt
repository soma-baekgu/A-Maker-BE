package com.backgu.amaker.domain.chat

import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.domain.event.ReplyEvent
import com.backgu.amaker.domain.event.TaskEvent
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class Chat(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
    var content: String,
    val chatType: ChatType,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun createTaskEvent(
        deadLine: LocalDateTime,
        notificationStartHour: Int,
        notificationStartMinute: Int,
        notificationInterval: Int,
        eventDetails: String,
    ) = TaskEvent(
        id = id,
        eventTitle = content,
        deadLine = deadLine,
        notificationStartTime =
            deadLine
                .minusHours(notificationStartHour.toLong())
                .minusMinutes(notificationStartMinute.toLong()),
        notificationInterval = notificationInterval,
        eventDetails = eventDetails,
    )

    fun createReplyEvent(
        deadLine: LocalDateTime,
        notificationStartHour: Int,
        notificationStartMinute: Int,
        notificationInterval: Int,
        eventDetails: String,
    ) = ReplyEvent(
        id = id,
        eventTitle = content,
        deadLine = deadLine,
        notificationStartTime =
            deadLine
                .minusHours(notificationStartHour.toLong())
                .minusMinutes(notificationStartMinute.toLong()),
        notificationInterval = notificationInterval,
        eventDetails = eventDetails,
    )

    fun createReactionEvent(
        deadLine: LocalDateTime,
        notificationStartHour: Int,
        notificationStartMinute: Int,
        notificationInterval: Int,
    ) = ReactionEvent(
        id = id,
        eventTitle = content,
        deadLine = deadLine,
        notificationStartTime =
            deadLine
                .minusHours(notificationStartHour.toLong())
                .minusMinutes(notificationStartMinute.toLong()),
        notificationInterval = notificationInterval,
    )

    fun createDefaultChatWithUser(user: User) =
        DefaultChatWithUser(
            id = id,
            chatRoomId = chatRoomId,
            content = content,
            chatType = chatType,
            createdAt = createdAt,
            updatedAt = updatedAt,
            user = user,
        )

    fun createEventChatWithUser(
        event: Event,
        user: User,
        assignedUsers: List<User>,
    ) = EventChatWithUser(
        id = id,
        chatRoomId = chatRoomId,
        content =
            event.createEventWithUser(
                users = assignedUsers,
                finishedCount = 0,
                totalAssignedCount = assignedUsers.size,
            ),
        chatType = chatType,
        createdAt = createdAt,
        updatedAt = updatedAt,
        user = user,
    )
}
