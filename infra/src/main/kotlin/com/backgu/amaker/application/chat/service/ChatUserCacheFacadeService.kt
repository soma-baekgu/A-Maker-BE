package com.backgu.amaker.application.chat.service

import com.backgu.amaker.application.chat.event.DefaultChatSaveEvent
import com.backgu.amaker.application.chat.event.EventChatSaveEvent
import com.backgu.amaker.application.chat.event.FinishedCountUpdateEvent
import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.user.service.UserCacheService
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.domain.chat.ChatWithUser
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.redis.chat.data.ChatWithUserCache
import com.backgu.amaker.infra.redis.chat.data.DefaultChatWithUserCache
import com.backgu.amaker.infra.redis.chat.data.EventChatWithUserCache
import com.backgu.amaker.infra.redis.chat.repository.ChatPipelinedQueryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class ChatUserCacheFacadeService(
    private val chatCacheService: ChatCacheService,
    private val chatPipelinedQueryRepository: ChatPipelinedQueryRepository,
    private val userCacheService: UserCacheService,
    private val chatRoomUserCacheService: ChatRoomUserCacheService,
    private val userService: UserService,
    private val chatService: ChatService,
    private val eventAssignedUserService: EventAssignedUserService,
) {
    @TransactionalEventListener
    fun saveDefaultChat(event: DefaultChatSaveEvent) {
        chatCacheService.saveChat(event.chatRoomId, DefaultChatWithUserCache.of(event.defaultChatWithUser))
    }

    @TransactionalEventListener
    fun saveEventChat(event: EventChatSaveEvent) {
        chatCacheService.saveChat(event.chatRoomId, EventChatWithUserCache.of(event.eventChatWithUser))
    }

    @TransactionalEventListener
    fun updateFinishedCount(event: FinishedCountUpdateEvent) {
        val chat = chatService.getById(event.chatId)
        val size = eventAssignedUserService.findAllByEventId(chat.id).filter { it.isFinished }.size

        chatCacheService.updateFinishedCount(
            chat.chatRoomId,
            event.chatId,
            size,
        )
    }

    fun findChat(
        chatRoomId: Long,
        chatId: Long,
    ): ChatWithUser<*>? {
        val chat = chatCacheService.findChat(chatRoomId, chatId) ?: return null

        val user =
            userCacheService.getUserById(chat.userId) ?: userService.getById(chat.userId).also { fetchedUser ->
                userCacheService.save(fetchedUser)
            }

        return when (chat) {
            is DefaultChatWithUserCache -> chat.toDomain(user)
            is EventChatWithUserCache -> {
                val userIds = chat.content.users
                val cachedUsers = userCacheService.findAllByUserIds(userIds)
                val missingUserIds = userIds.filterNot { cachedUsers.any { user -> user.id == it } }
                val fetchedUsers =
                    userService.getAllByUserIds(missingUserIds).onEach { userCacheService.save(it) }
                val allUsers = cachedUsers + fetchedUsers

                chat.toDomain(user, chat.content.toDomain(allUsers))
            }
        }
    }

    fun findPreviousChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUser<*>> {
        val cachedUsersMap =
            userCacheService
                .findAllByUserIds(chatRoomUserCacheService.findUserIds(chatRoomId).toList())
                .associateBy { it.id }

        return chatCacheService
            .findPreviousChats(chatRoomId, cursor, count)
            .reversed()
            .map { chat -> mapChatToDto(chatRoomId, chat, cachedUsersMap) }
    }

    fun findAfterChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUser<*>>? {
        chatCacheService.findFirstChat(chatRoomId)?.takeIf { it.id <= cursor } ?: return null

        val cachedUsersMap =
            userCacheService
                .findAllByUserIds(chatRoomUserCacheService.findUserIds(chatRoomId).toList())
                .associateBy { it.id }

        return chatCacheService
            .findAfterChats(chatRoomId, cursor, count)
            .map { chat -> mapChatToDto(chatRoomId, chat, cachedUsersMap) }
    }

    fun findAfterChatsWithPipelinedQuery(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUser<*>>? =
        chatPipelinedQueryRepository.findAfterChats(chatRoomId, cursor, count)?.let { chats ->
            userCacheService
                .findAllByUserIds(chatRoomUserCacheService.findUserIds(chatRoomId).toList())
                .associateBy { it.id }
                .let { cachedUsersMap ->
                    chats.map { chat -> mapChatToDto(chatRoomId, chat, cachedUsersMap) }
                }
        }

    private fun mapChatToDto(
        chatRoomId: Long,
        chat: ChatWithUserCache<*>,
        cachedUsersMap: Map<String, User>,
    ): ChatWithUser<*> {
        val user =
            userCacheService.getUserById(chat.userId)
                ?: userService.getById(chat.userId).also { fetchedUser ->
                    userCacheService.save(fetchedUser)
                }

        return when (chat) {
            is DefaultChatWithUserCache -> chat.toDomain(user)
            is EventChatWithUserCache -> {
                val userIds = chat.content.users

                val missingUserIds = userIds.filterNot { cachedUsersMap.containsKey(it) }

                val fetchedUsersMap =
                    userService
                        .getAllByUserIds(missingUserIds)
                        .associateBy { it.id }
                        .onEach {
                            chatRoomUserCacheService.addUser(chatRoomId, it.value.id)
                            userCacheService.save(it.value)
                        }

                val allUsers = userIds.mapNotNull { cachedUsersMap[it] ?: fetchedUsersMap[it] }

                return chat.toDomain(user, chat.content.toDomain(allUsers))
            }
        }
    }
}
