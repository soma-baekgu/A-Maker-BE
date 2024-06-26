package com.backgu.amaker.workspace.domain

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.common.domain.BaseTime
import com.backgu.amaker.user.domain.User

class Workspace(
    val id: Long = 0L,
    var name: String,
    var thumbnail: String = "/images/default_thumbnail.png",
) : BaseTime() {
    fun assignLeader(user: User): WorkspaceUser = WorkspaceUser(userId = user.id, workspaceId = id, workspaceRole = WorkspaceRole.LEADER)

    fun createGroupChatRoom(): ChatRoom = ChatRoom(workspaceId = id, chatRoomType = ChatRoomType.GROUP)
}
