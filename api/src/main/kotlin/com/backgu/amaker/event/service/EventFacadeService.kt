package com.backgu.amaker.event.service

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.service.ChatRoomService
import com.backgu.amaker.chat.service.ChatRoomUserService
import com.backgu.amaker.chat.service.ChatService
import com.backgu.amaker.event.dto.ReplyEventCreateDto
import com.backgu.amaker.event.dto.ReplyEventDto
import com.backgu.amaker.user.service.UserService
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
                chat.crateReplyEvent(
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
}
