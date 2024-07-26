package com.backgu.amaker.domain.workspace

import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.common.BaseTime
import com.backgu.amaker.domain.user.User

class Workspace(
    val id: Long = 0L,
    var name: String,
    var thumbnail: String = "/images/default_thumbnail.png",
) : BaseTime() {
    fun assignLeader(user: User): WorkspaceUser = WorkspaceUser.makeWorkspaceLeader(workspace = this, user = user)

    fun createCustomChatRoom(chatRoomName: String): ChatRoom =
        ChatRoom(workspaceId = id, name = chatRoomName, chatRoomType = ChatRoomType.CUSTOM)

    fun inviteWorkspace(user: User): WorkspaceUser = WorkspaceUser(userId = user.id, workspaceId = id)

    fun createDefaultChatRoom(): ChatRoom = ChatRoom(workspaceId = id, name = "일반 채팅", chatRoomType = ChatRoomType.DEFAULT)
}
