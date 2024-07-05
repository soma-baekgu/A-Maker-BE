package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.workspace.domain.Workspace
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    @Transactional
    fun save(chatRoom: ChatRoom): ChatRoom = chatRoomRepository.save(ChatRoomEntity.of(chatRoom)).toDomain()

    fun getGroupChatRoomByWorkspace(workspace: Workspace): ChatRoom =
        chatRoomRepository.findByWorkspaceIdAndChatRoomType(workspace.id, ChatRoomType.GROUP)?.toDomain()
            ?: run {
                // TODO : 로깅 전략 세워야 함
                logger.error { "Group ChatRoom not found in Workspace ${workspace.id}" }
                throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
            }

    fun findGroupChatRoomByWorkspaceId(workspaceId: Long): ChatRoom =
        chatRoomRepository.findByWorkspaceIdAndChatRoomType(workspaceId, ChatRoomType.GROUP)?.toDomain() ?: run {
            logger.error { "Group ChatRoom not found in Workspace $workspaceId" }
            throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
        }

    fun getById(chatRoomId: Long): ChatRoom =
        chatRoomRepository.findByIdOrNull(chatRoomId)?.toDomain() ?: run {
            logger.error { "ChatRoom not found $chatRoomId" }
            throw BusinessException(StatusCode.CHAT_ROOM_NOT_FOUND)
        }
}
