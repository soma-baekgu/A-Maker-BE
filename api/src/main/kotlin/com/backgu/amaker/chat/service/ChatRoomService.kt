package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.workspace.domain.Workspace
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
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
            // TODO : 공통 에러처리 추후에 해줘야함
            ?: run {
                logger.error { "Group ChatRoom not found in Workspace ${workspace.id}" }
                throw EntityNotFoundException("Group ChatRoom not found in Workspace ${workspace.id}")
            }
}
