package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.user.repository.UserRepository
import com.backgu.amaker.workspace.domain.WorkspaceRole
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.dto.WorkspaceDto
import com.backgu.amaker.workspace.dto.WorkspacesDto
import com.backgu.amaker.workspace.jpa.WorkspaceEntity
import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

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
    fun createWorkspace(
        userId: UUID,
        request: WorkspaceCreateDto,
    ): Long {
        val user =
            userRepository.findByIdOrNull(userId) ?: run {
                logger.error { "User not found : $userId" }
                throw EntityNotFoundException("User not found : $userId")
            }

        val workspaceEntity =
            workspaceRepository.save(
                WorkspaceEntity(
                    name = request.name,
                ),
            )

        workspaceUserRepository.save(
            WorkspaceUserEntity(
                userId = user.id,
                workspaceId = workspaceEntity.id,
                workspaceRole = WorkspaceRole.LEADER,
            ),
        )

        val chatRoomEntity =
            chatRoomRepository.save(
                ChatRoomEntity(
                    workspaceId = workspaceEntity.id,
                    chatRoomType = ChatRoomType.GROUP,
                ),
            )

        chatRoomUserRepository.save(
            ChatRoomUserEntity(
                userId = user.id,
                chatRoomId = chatRoomEntity.id,
            ),
        )

        return workspaceEntity.id
    }

    fun findWorkspaces(userId: UUID): WorkspacesDto {
        val user =
            userRepository.findByIdOrNull(userId) ?: run {
                logger.error { "User not found : $userId" }
                throw EntityNotFoundException("User not found : $userId")
            }

        val workspaceUsers = workspaceUserRepository.findWorkspaceIdsByUserId(user.id)

        val workspaceDtos =
            workspaceRepository.findByWorkspaceIds(workspaceUsers).map {
                WorkspaceDto(
                    id = it.id,
                    name = it.name,
                    thumbnail = it.thumbnail,
                )
            }

        return WorkspacesDto(
            userId = user.id,
            workspaces = workspaceDtos,
        )
    }

    fun getDefaultWorkspace(userId: UUID): WorkspaceDto {
        val user =
            userRepository.findByIdOrNull(userId) ?: run {
                logger.error { "User not found : $userId" }
                throw EntityNotFoundException("User not found : $userId")
            }

        return workspaceRepository.getDefaultWorkspaceByUserId(user.id)?.let {
            WorkspaceDto(
                id = it.id,
                name = it.name,
                thumbnail = it.thumbnail,
            )
        } ?: run {
            logger.error { "Default workspace not found : $userId" }
            throw EntityNotFoundException("Default workspace not found : $userId")
        }
    }
}
