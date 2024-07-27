package com.backgu.amaker.api.chat.service

import com.backgu.amaker.api.chat.dto.ChatCreateDto
import com.backgu.amaker.api.chat.dto.ChatListDto
import com.backgu.amaker.api.chat.dto.ChatQuery
import com.backgu.amaker.api.chat.dto.ChatWithUserDto
import com.backgu.amaker.api.chat.dto.DefaultChatWithUserDto
import com.backgu.amaker.api.chat.dto.EventChatWithUserDto
import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.api.event.dto.EventWithUserDto
import com.backgu.amaker.api.event.service.EventAssignedUserService
import com.backgu.amaker.api.event.service.EventService
import com.backgu.amaker.api.user.service.UserService
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomUser
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.event.EventAssignedUser
import com.backgu.amaker.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatFacadeService(
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val chatService: ChatService,
    private val userService: UserService,
    private val eventService: EventService,
    private val eventAssignedUserService: EventAssignedUserService,
) {
    @Transactional
    fun createChat(
        chatCreateDto: ChatCreateDto,
        userId: String,
        chatRoomId: Long,
        chatType: ChatType = ChatType.GENERAL,
    ): DefaultChatWithUserDto {
        val user: User = userService.getById(userId)
        val chatRoom: ChatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)
        val chat: Chat = chatService.save(chatRoom.createChat(user, chatCreateDto.content, chatType))
        chatRoomService.save(chatRoom.updateLastChatId(chat))

        return DefaultChatWithUserDto.of(chat, user)
    }

    @Transactional
    fun getPreviousChat(
        userId: String,
        chatQuery: ChatQuery,
    ): ChatListDto {
        markMostRecentChatAsRead(chatQuery.chatRoomId, userId)
        val chatList = chatService.findPreviousChatList(chatQuery.chatRoomId, chatQuery.cursor, chatQuery.size)

        val eventMap =
            eventService.findEventByIdsToMap(chatList.filter { ChatType.isEventChat(it.chatType) }.map { it.id })
        val eventUserMap = eventAssignedUserService.findByEventIdsToEventIdMapped(eventMap.keys.toList())
        val userMap = userService.findAllByUserIdsToMap(eventUserMap.values.flatten().map { it.userId })

        return ChatListDto.of(
            chatQuery,
            chatList.map { mapToChatWithUser(it, eventMap, eventUserMap, userMap) },
        )
    }

    @Transactional
    fun getAfterChat(
        userId: String,
        chatQuery: ChatQuery,
    ): ChatListDto {
        markMostRecentChatAsRead(chatQuery.chatRoomId, userId)
        val chatList = chatService.findAfterChatList(chatQuery.chatRoomId, chatQuery.cursor, chatQuery.size)

        val eventMap =
            eventService.findEventByIdsToMap(chatList.filter { ChatType.isEventChat(it.chatType) }.map { it.id })
        val eventUserMap = eventAssignedUserService.findByEventIdsToEventIdMapped(eventMap.keys.toList())
        val userMap = userService.findAllByUserIdsToMap(eventUserMap.values.flatten().map { it.userId })

        return ChatListDto.of(
            chatQuery,
            chatList.map { mapToChatWithUser(it, eventMap, eventUserMap, userMap) },
        )
    }

    @Transactional
    fun getRecentChat(
        userId: String,
        chatRoomId: Long,
    ): ChatWithUserDto<*> {
        val chatRoomUser = markMostRecentChatAsRead(chatRoomId, userId)
        val chat = chatService.getOneWithUser(chatRoomUser.lastReadChatId)

        if (!ChatType.isEventChat(chat.chatType)) return chat

        val event = eventService.getEventById(chat.id)
        val eventAssignedUsers = eventAssignedUserService.findAllByEventId(event.id)
        val userMap = userService.findAllByUserIdsToMap(eventAssignedUsers.map { it.userId })
        val eventUsers = eventAssignedUsers.mapNotNull { userMap[it.userId] }

        return EventChatWithUserDto.of(chat, EventWithUserDto.of(event, eventUsers))
    }

    private fun markMostRecentChatAsRead(
        chatRoomId: Long,
        userId: String,
    ): ChatRoomUser {
        val chatRoom = chatRoomService.getById(chatRoomId)
        val chatRoomUser = chatRoomUserService.getByUserIdAndChatRoomId(userId, chatRoomId)
        chatRoomUserService.save(chatRoomUser.readLastChatOfChatRoom(chatRoom))
        return chatRoomUser
    }

    private fun mapToChatWithUser(
        chat: DefaultChatWithUserDto,
        eventMap: Map<Long, Event>,
        eventUserMap: Map<Long, List<EventAssignedUser>>,
        userMap: Map<String, User>,
    ): ChatWithUserDto<*> =
        if (ChatType.isEventChat(chat.chatType)) {
            val event = eventMap[chat.id] ?: throw BusinessException(StatusCode.EVENT_NOT_FOUND)
            val eventUsers = eventUserMap[event.id]?.mapNotNull { userMap[it.userId] } ?: emptyList()
            EventChatWithUserDto.of(chat, EventWithUserDto.of(event, eventUsers))
        } else {
            chat
        }
}
