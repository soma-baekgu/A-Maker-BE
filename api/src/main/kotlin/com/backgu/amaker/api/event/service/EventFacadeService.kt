package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.chat.service.ChatRoomService
import com.backgu.amaker.api.chat.service.ChatRoomUserService
import com.backgu.amaker.api.chat.service.ChatService
import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.api.event.dto.ReplyEventCreateDto
import com.backgu.amaker.api.event.dto.ReplyEventDetailDto
import com.backgu.amaker.api.event.dto.ReplyEventDto
import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.api.user.service.UserService
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventFacadeService(
    private val userService: UserService,
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val chatService: ChatService,
    private val replyEventService: ReplyEventService,
    private val eventAssignedUserService: EventAssignedUserService,
) {
    @Transactional
    fun createReplyEvent(
        userId: String,
        chatRoomId: Long,
        replyEventCreateDto: ReplyEventCreateDto,
    ): ReplyEventDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat: Chat = chatService.save(chatRoom.createChat(user, replyEventCreateDto.eventTitle, ChatType.REPLY))
        chatRoomService.save(chatRoom.updateLastChatId(chat))

        val replyEvent =
            replyEventService.save(
                chat.createReplyEvent(
                    replyEventCreateDto.deadLine,
                    replyEventCreateDto.notificationStartHour,
                    replyEventCreateDto.notificationStartMinute,
                    replyEventCreateDto.interval,
                    replyEventCreateDto.eventDetails,
                ),
            )

        val users = userService.getAllByUserEmails(replyEventCreateDto.assignees)
        chatRoomUserService.validateUsersInChatRoom(users, chatRoom)

        eventAssignedUserService.saveAll(replyEvent.createAssignedUsers(users))

        return ReplyEventDto.of(replyEvent)
    }

    @Transactional
    fun getReplyEvent(
        userId: String,
        chatRoomId: Long,
        eventId: Long,
    ): ReplyEventDetailDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat = chatService.getById(eventId)
        val eventAssignedUsers = eventAssignedUserService.findAllByEventId(eventId)
        val eventAssignedUserIds = eventAssignedUsers.map { it.userId }

        val users = userService.findAllByUserIdsToMap(eventAssignedUserIds.union(listOf(chat.userId)).toList())

        val replyEvent = replyEventService.getById(eventId)

        val (finishedUsers, waitingUsers) = eventAssignedUsers.partition { it.isFinished }

        return ReplyEventDetailDto.of(
            replyEvent = replyEvent,
            eventCreator = UserDto.of(users[chat.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND)),
            finishUser =
                finishedUsers.map {
                    UserDto.of(
                        users[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
            waitingUser =
                waitingUsers.map {
                    UserDto.of(
                        users[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
        )
    }
}
