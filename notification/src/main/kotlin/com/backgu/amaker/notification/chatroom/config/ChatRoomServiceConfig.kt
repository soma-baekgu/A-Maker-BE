package com.backgu.amaker.notification.chatroom.config

import com.backgu.amaker.application.chat.service.ChatRoomUserService
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatRoomServiceConfig {
    @Bean
    fun chatRoomUserService(chatRoomUserRepository: ChatRoomUserRepository) = ChatRoomUserService(chatRoomUserRepository)
}
