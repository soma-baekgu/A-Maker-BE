package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.workspace.domain.Workspace
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
}
