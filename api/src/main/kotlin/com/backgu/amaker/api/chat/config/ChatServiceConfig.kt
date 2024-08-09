package com.backgu.amaker.api.chat.config

import com.backgu.amaker.application.chat.service.ChatRoomService
import com.backgu.amaker.application.chat.service.ChatRoomUserService
import com.backgu.amaker.application.chat.service.ChatService
import com.backgu.amaker.infra.jpa.chat.repository.ChatRepository
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomRepository
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatServiceConfig(
    val chatRepository: ChatRepository,
    val chatRoomUserRepository: ChatRoomUserRepository,
    val chatRoomRepository: ChatRoomRepository,
) {
    @Bean
    fun chatService() = ChatService(chatRepository)

    @Bean
    fun chatRoomUserService() = ChatRoomUserService(chatRoomUserRepository)

    @Bean
    fun chatRoomService() = ChatRoomService(chatRoomRepository)
}
