package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.ChatRoomDto
import com.backgu.amaker.chat.service.ChatRoomService
import com.backgu.amaker.chat.service.ChatRoomUserService
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.mail.service.EmailEventService
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.service.UserService
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.dto.WorkspaceDto
import com.backgu.amaker.workspace.dto.WorkspaceUserDto
import com.backgu.amaker.workspace.dto.WorkspacesDto
import com.backgu.amaker.workspace.event.WorkspaceInvitedEvent
import com.backgu.amaker.workspace.event.WorkspaceJoinedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class WorkspaceFacadeService(
    private val userService: UserService,
    private val workspaceService: WorkspaceService,
    private val workspaceUserService: WorkspaceUserService,
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val emailEventService: EmailEventService,
) {
    @Transactional
    fun createWorkspace(
        userId: String,
        workspaceCreateDto: WorkspaceCreateDto,
    ): WorkspaceDto {
        val leader: User = userService.getById(userId)
        val workspace: Workspace = workspaceService.save(leader.createWorkspace(workspaceCreateDto.name))
        workspaceUserService.save(workspace.assignLeader(leader))
        emailEventService.publishEmailEvent(WorkspaceJoinedEvent(leader, workspace))

        val invitees = workspaceCreateDto.inviteesEmails.map { userService.getByEmail(it) }
        invitees.forEach {
            if (!leader.isNonInvitee(it)) throw BusinessException(StatusCode.INVALID_WORKSPACE_CREATE)
            workspaceUserService.save(workspace.inviteWorkspace(it))
            emailEventService.publishEmailEvent(WorkspaceInvitedEvent(it, workspace))
        }

        val chatRoom: ChatRoom = chatRoomService.save(workspace.createGroupChatRoom())
        chatRoomUserService.save(chatRoom.addUser(leader))

        return WorkspaceDto.of(workspace)
    }

    fun findWorkspaces(userId: String): WorkspacesDto {
        val user: User = userService.getById(userId)

        val workspaces: List<Workspace> =
            workspaceService.getWorkspaceByIds(workspaceUserService.findWorkspaceIdsByUser(user))

        return WorkspacesDto(
            userId = user.id,
            workspaces = workspaces.map { WorkspaceDto.of(it) },
        )
    }

    fun getDefaultWorkspace(userId: String): WorkspaceDto {
        val user: User = userService.getById(userId)
        return workspaceService.getDefaultWorkspaceByUserId(user).let { WorkspaceDto.of(it) }
    }

    fun getGroupChatRoom(
        workspaceId: Long,
        userId: String,
    ): ChatRoomDto {
        val user: User = userService.getById(userId)
        val workspace: Workspace = workspaceService.getWorkspaceById(workspaceId)

        workspaceUserService.validUserInWorkspace(user, workspace)

        return ChatRoomDto.of(chatRoomService.getGroupChatRoomByWorkspace(workspace))
    }

    @Transactional
    fun activateWorkspaceUser(
        userId: String,
        workspaceId: Long,
    ): WorkspaceUserDto {
        val user = userService.getById(userId)
        val workspace = workspaceService.getById(workspaceId)

        val workspaceUser = workspaceUserService.getWorkspaceUser(workspace, user)
        emailEventService.publishEmailEvent(WorkspaceJoinedEvent(user, workspace))
        workspaceUser.activate()
        workspaceUserService.save(workspaceUser)

        chatRoomService
            .findGroupChatRoomByWorkspaceId(workspaceId)
            .addUser(user)

        return WorkspaceUserDto.of(workspaceUser)
    }
}
