package com.backgu.amaker.api.chat.config

import com.backgu.amaker.application.chat.service.ChatCacheService
import com.backgu.amaker.application.chat.service.ChatQueryService
import com.backgu.amaker.application.chat.service.ChatRoomService
import com.backgu.amaker.application.chat.service.ChatRoomUserCacheService
import com.backgu.amaker.application.chat.service.ChatRoomUserService
import com.backgu.amaker.application.chat.service.ChatService
import com.backgu.amaker.application.chat.service.ChatUserCacheFacadeService
import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.user.service.UserCacheService
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.infra.jpa.chat.repository.ChatRepository
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomRepository
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.infra.redis.chat.repository.ChatCacheRepository
import com.backgu.amaker.infra.redis.chat.repository.ChatRoomUserCacheRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatServiceConfig {
    @Bean
    fun chatService(chatRepository: ChatRepository) = ChatService(chatRepository)

    @Bean
    fun chatRoomUserService(chatRoomUserRepository: ChatRoomUserRepository) = ChatRoomUserService(chatRoomUserRepository)

    @Bean
    fun chatRoomService(chatRoomRepository: ChatRoomRepository) = ChatRoomService(chatRoomRepository)

    @Bean
    fun chatQueryService(chatRepository: ChatRepository) = ChatQueryService(chatRepository)

    @Bean
    fun chatCacheService(chatCacheRepository: ChatCacheRepository) = ChatCacheService(chatCacheRepository)

    @Bean
    fun chatRoomUserCacheService(chatRoomUserCacheRepository: ChatRoomUserCacheRepository) =
        ChatRoomUserCacheService(chatRoomUserCacheRepository)

    @Bean
    fun chatUserCacheService(
        chatCacheService: ChatCacheService,
        userCacheService: UserCacheService,
        chatRoomUserCacheService: ChatRoomUserCacheService,
        userService: UserService,
        chatService: ChatService,
        eventAssignedUserService: EventAssignedUserService,
    ) = ChatUserCacheFacadeService(
        chatCacheService,
        userCacheService,
        chatRoomUserCacheService,
        userService,
        chatService,
        eventAssignedUserService,
    )
}
