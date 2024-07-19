package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.ChatRoomCreateDto
import com.backgu.amaker.chat.dto.ChatRoomDto
import com.backgu.amaker.user.service.UserService
import com.backgu.amaker.workspace.service.WorkspaceService
import com.backgu.amaker.workspace.service.WorkspaceUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatRoomFacadeService(
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val userService: UserService,
    private val workspaceService: WorkspaceService,
    private val workspaceUserService: WorkspaceUserService,
) {
    @Transactional
    fun createChatRoom(
        userId: String,
        workspaceId: Long,
        chatRoomCreateDto: ChatRoomCreateDto,
    ): ChatRoomDto {
        val user = userService.getById(userId)
        val workspace = workspaceService.getById(workspaceId)
        workspaceUserService.verifyUserHasAdminPrivileges(workspace, user)

        val chatRoom: ChatRoom = chatRoomService.save(workspace.createCustomChatRoom(chatRoomCreateDto.name))
        chatRoomUserService.save(chatRoom.addUser(user))
        return ChatRoomDto.of(chatRoom)
    }
}
