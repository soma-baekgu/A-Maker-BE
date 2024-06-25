package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.user.repository.UserRepository
import com.backgu.amaker.workspace.domain.Role
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class WorkspaceService(
    private val userRepository: UserRepository,
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceUserRepository: WorkspaceUserRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomUserRepository: ChatRoomUserRepository,
) {
    @Transactional
    fun createWorkspace(request: WorkspaceCreateDto): Long {
        val user =
            userRepository.findByIdOrNull(request.userId) ?: run {
                logger.error { "User not found : ${request.userId}" }
                throw EntityNotFoundException("User not found : ${request.userId}")
            }

        val workspace =
            workspaceRepository.save(
                Workspace(
                    name = request.name,
                ),
            )

        workspaceUserRepository.save(
            WorkspaceUser(
                userId = user.id,
                workspaceId = workspace.id,
                role = Role.LEADER,
            ),
        )

        val chatRoom =
            chatRoomRepository.save(
                ChatRoom(
                    workspaceId = workspace.id,
                    chatRoomType = ChatRoomType.GROUP,
                ),
            )

        chatRoomUserRepository.save(
            ChatRoomUser(
                userId = user.id,
                chatRoomId = chatRoom.id,
            ),
        )

        return workspace.id
    }
}
