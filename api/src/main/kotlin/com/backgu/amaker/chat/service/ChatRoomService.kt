package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    @Transactional
    fun save(chatRoom: ChatRoom): ChatRoom = chatRoomRepository.save(ChatRoomEntity.of(chatRoom)).toDomain()
}
