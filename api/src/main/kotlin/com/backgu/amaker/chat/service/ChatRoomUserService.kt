package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatRoomUserService(
    private var chatRoomUserRepository: ChatRoomUserRepository,
) {
    @Transactional
    fun save(chatRoomUser: ChatRoomUser): ChatRoomUser = chatRoomUserRepository.save(ChatRoomUserEntity.of(chatRoomUser)).toDomain()

    fun getByUserIdAndChatRoomId(
        userId: String,
        chatRoomId: Long,
    ): ChatRoomUser =
        chatRoomUserRepository.findByUserIdAndChatRoomId(userId, chatRoomId)?.toDomain() ?: run {
            throw BusinessException(StatusCode.CHAT_ROOM_USER_NOT_FOUND)
        }

    fun validateUserInChatRoom(
        user: User,
        chatRoom: ChatRoom,
    ) {
        if (!chatRoomUserRepository.existsByUserIdAndChatRoomId(user.id, chatRoom.id)) {
            throw BusinessException(StatusCode.CHAT_ROOM_USER_NOT_FOUND)
        }
    }
}
