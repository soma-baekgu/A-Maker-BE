package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.chat.dto.ChatRoomCreateDto
import com.backgu.amaker.chat.dto.ChatRoomDto
import com.backgu.amaker.chat.dto.ChatRoomsViewDto
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.service.UserService
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.service.WorkspaceService
import com.backgu.amaker.workspace.service.WorkspaceUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatRoomFacadeService(
    private val workspaceService: WorkspaceService,
    private val workspaceUserService: WorkspaceUserService,
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val chatService: ChatService,
    private val userService: UserService,
) {
    fun findChatRoomsJoined(
        userId: String,
        workspaceId: Long,
    ): ChatRoomsViewDto {
        val user: User = userService.getById(userId)
        val workspace: Workspace = workspaceService.getById(workspaceId)

        workspaceUserService.validUserInWorkspace(user, workspace)

        // 유저의 채팅방 목록 조회
        val chatRoomMys: List<ChatRoomUser> = chatRoomUserService.findAllByUser(user)
        // 채팅방 도메인 목록 조회
        val chatRooms: List<ChatRoom> = chatRoomService.findChatRoomsByChatRoomIds(chatRoomMys.map { it.chatRoomId })

        // 채팅방의 마지막 채팅 조회
        val lastChats: Map<Long, Chat> =
            chatService.findAllByIds(chatRooms.mapNotNull { it.lastChatId }).associateBy { it.chatRoomId }

        // 채팅방에 참여한 유저 조회
        val chatRoomUsers: List<ChatRoomUser> = chatRoomUserService.findAllByChatRoomIds(chatRooms.map { it.id })
        val chatRoomUserMap: Map<Long, List<String>> =
            chatRoomUsers
                .groupBy { it.chatRoomId }
                .mapValues { entry -> entry.value.map { it.userId } }

        // 채팅방에 참여한 유저들의 도메인 조회
        val userMap: Map<String, User> =
            userService.findAllByUserIds(chatRoomUsers.map { it.userId }).associateBy { it.id }

        // 채팅방의 읽지 않은 채팅 수 조회
        val unreadChatCountMap =
            chatRoomMys.associate { chatRoomMy ->
                chatRoomMy.chatRoomId to
                    chatService.getUnreadChatCount(
                        chatRoomId = chatRoomMy.chatRoomId,
                        lastReadChatId = chatRoomMy.lastReadChatId,
                    )
            }

        // 채팅방 목록을 반환
        return ChatRoomsViewDto.of(
            chatRooms = chatRooms,
            chatRoomUsers = chatRoomUserMap,
            chatRoomWithLastChat = lastChats,
            users = userMap,
            unreadChatCountMap = unreadChatCountMap,
        )
    }

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
