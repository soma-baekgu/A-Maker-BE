package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.ChatListDto
import com.backgu.amaker.chat.dto.ChatQuery
import com.backgu.amaker.chat.dto.ChatWithUserDto
import com.backgu.amaker.chat.dto.GeneralChatCreateDto
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatFacadeService(
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val chatService: ChatService,
    private val userService: UserService,
) {
    @Transactional
    fun createGeneralChat(
        generalChatCreateDto: GeneralChatCreateDto,
        userId: String,
        chatRoomId: Long,
    ): ChatWithUserDto {
        val user: User = userService.getById(userId)
        val chatRoom: ChatRoom = chatRoomService.getById(chatRoomId)

        chatRoomUserService.validateUserInChatRoom(user, chatRoom)
        val chat = chatService.save(chatRoom.createGeneralChat(user, chatRoom, generalChatCreateDto.content))
        chatRoomService.save(chatRoom.updateLastChatId(chat))

        return ChatWithUserDto.of(chat, user)
    }

    @Transactional
    fun getPreviousChat(
        userId: String,
        chatQuery: ChatQuery,
    ): ChatListDto {
        val chatRoom = chatRoomService.getById(chatQuery.chatRoomId)
        val chatRoomUser = chatRoomUserService.getByUserIdAndChatRoomId(userId, chatQuery.chatRoomId)
        chatRoomUserService.save(chatRoomUser.readLastChatOfChatRoom(chatRoom))

        val chatList: List<ChatWithUserDto> =
            chatService.findPreviousChatList(chatQuery.chatRoomId, chatQuery.cursor, chatQuery.size)
        return ChatListDto.of(chatQuery, chatList)
    }

    @Transactional
    fun getAfterChat(
        userId: String,
        chatQuery: ChatQuery,
    ): ChatListDto {
        val chatRoom = chatRoomService.getById(chatQuery.chatRoomId)
        val chatRoomUser = chatRoomUserService.getByUserIdAndChatRoomId(userId, chatQuery.chatRoomId)
        chatRoomUserService.save(chatRoomUser.readLastChatOfChatRoom(chatRoom))

        val chatList: List<ChatWithUserDto> =
            chatService.findAfterChatList(chatQuery.chatRoomId, chatQuery.cursor, chatQuery.size)
        return ChatListDto.of(chatQuery, chatList)
    }
}
