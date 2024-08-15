package com.backgu.amaker.domain.workspace

import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.common.BaseTime
import com.backgu.amaker.domain.user.User

class Workspace(
    val id: Long = 0L,
    var name: String,
    var thumbnail: String = "/images/default_thumbnail.png",
    var belongingNumber: Int = 0,
    var workspacePlan: WorkspacePlan = WorkspacePlan.BASIC,
) : BaseTime() {
    companion object {
        const val DOMAIN_NAME = "WORKSPACE"
    }

    fun isAvailToJoin(): Boolean = belongingNumber < workspacePlan.belongingLimit

    fun assignLeader(user: User): WorkspaceUser {
        increaseMember()
        return WorkspaceUser.makeWorkspaceLeader(workspace = this, user = user)
    }

    fun createCustomChatRoom(chatRoomName: String): ChatRoom =
        ChatRoom(workspaceId = id, name = chatRoomName, chatRoomType = ChatRoomType.CUSTOM)

    fun inviteWorkspace(user: User): WorkspaceUser = WorkspaceUser(userId = user.id, workspaceId = id)

    fun increaseMember(): Workspace {
        belongingNumber++
        return this
    }

    fun createDefaultChatRoom(): ChatRoom = ChatRoom(workspaceId = id, name = "일반 채팅", chatRoomType = ChatRoomType.DEFAULT)

    override fun toString(): String {
        return "Workspace(id=$id, name='$name', thumbnail='$thumbnail', belongingNumber=$belongingNumber, workspacePlan=$workspacePlan)"
    }
}
