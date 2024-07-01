package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatRoomUserService(
    private var chatRoomUserRepository: ChatRoomUserRepository,
) {
    @Transactional
    fun save(chatRoomUser: ChatRoomUser): ChatRoomUser = chatRoomUserRepository.save(ChatRoomUserEntity.of(chatRoomUser)).toDomain()
}
