package com.backgu.amaker.api.chat.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.jpa.chat.entity.ChatRoomEntity
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    @Transactional
    fun save(chatRoom: ChatRoom): ChatRoom = chatRoomRepository.save(ChatRoomEntity.of(chatRoom)).toDomain()

    fun getDefaultChatRoomByWorkspace(workspace: Workspace): ChatRoom =
        chatRoomRepository.findByWorkspaceIdAndChatRoomType(workspace.id, ChatRoomType.DEFAULT)?.toDomain()
            ?: run {
                throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
            }

    fun getDefaultChatRoomByWorkspaceId(workspaceId: Long): ChatRoom =
        chatRoomRepository.findByWorkspaceIdAndChatRoomType(workspaceId, ChatRoomType.DEFAULT)?.toDomain() ?: run {
            throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
        }

    fun getById(chatRoomId: Long): ChatRoom =
        chatRoomRepository.findByIdOrNull(chatRoomId)?.toDomain() ?: run {
            throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
        }

    fun findChatRoomsByChatRoomIds(chatRoomIds: List<Long>): List<ChatRoom> =
        chatRoomRepository.findByIdIn(chatRoomIds).map { it.toDomain() }

    fun findChatRoomsByWorkspaceId(workspaceId: Long): List<ChatRoom> =
        chatRoomRepository.findByWorkspaceId(workspaceId).map { it.toDomain() }

    fun getChatRoomByWorkspaceIdAndChatRoomId(
        workspaceId: Long,
        chatRoomId: Long,
    ): ChatRoom =
        chatRoomRepository.findByIdAndWorkspaceId(chatRoomId, workspaceId)?.toDomain() ?: run {
            throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
        }

    fun findNotRegisteredChatRoomsByWorkspaceId(
        workspaceId: Long,
        userId: String,
    ): List<ChatRoom> =
        chatRoomRepository.findByWorkspaceIdWithNotRegisteredUser(workspaceId, userId).map {
            it.toDomain()
        }

    fun findChatRoomsByChatRoomIdsAndWorkspaceId(
        chatRoomIds: List<Long>,
        workspaceId: Long,
    ): List<ChatRoom> = chatRoomRepository.findByIdInAndWorkspaceId(chatRoomIds, workspaceId).map { it.toDomain() }
}
