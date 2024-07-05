package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.user.domain.User
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class ChatRoomUserService(
    private var chatRoomUserRepository: ChatRoomUserRepository,
) {
    @Transactional
    fun save(chatRoomUser: ChatRoomUser): ChatRoomUser = chatRoomUserRepository.save(ChatRoomUserEntity.of(chatRoomUser)).toDomain()

    fun validateUserInChatRoom(
        user: User,
        chatRoom: ChatRoom,
    ) {
        if (!chatRoomUserRepository.existsByUserIdAndChatRoomId(user.id, chatRoom.id)) {
            logger.error { "User ${user.id} is not in ChatRoom ${chatRoom.id}" }
            throw EntityNotFoundException("User ${user.id} is not in ChatRoom ${chatRoom.id}")
        }
    }
}
