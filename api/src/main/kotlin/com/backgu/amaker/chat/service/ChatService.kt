package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomUserRepository: ChatRoomUserRepository,
)
