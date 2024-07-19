package com.backgu.amaker.workspace.domain

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Workspace 테스트")
class WorkspaceTest {
    @Test
    @DisplayName("워크스페이스에서 leader를 배정할 수 있다")
    fun assignLeader() {
        // given
        val workspace = Workspace(name = "test")
        val leader =
            User(id = "leader", name = "leader", email = "leader@gmail.com", picture = "/images/default_thumbnail.png")

        // when
        val workspaceUser: WorkspaceUser = workspace.assignLeader(leader)

        // then
        assertThat(workspaceUser).isNotNull
        assertThat(workspaceUser.workspaceRole).isEqualTo(WorkspaceRole.LEADER)
        assertThat(workspaceUser.status).isEqualTo(WorkspaceUserStatus.ACTIVE)
        assertThat(workspaceUser.userId).isEqualTo(leader.id)
        assertThat(workspaceUser.workspaceId).isEqualTo(workspace.id)
    }

    @Test
    @DisplayName("워크스페이스에서 기본 채팅방을 생성할 수 있다")
    fun createDefaultChatRoom() {
        // given
        val workspace = Workspace(name = "test")
        val leader =
            User(id = "leader", name = "leader", email = "leader@gmail.com", picture = "/images/default_thumbnail.png")
        workspace.assignLeader(leader)

        // when
        val chatRoom: ChatRoom = workspace.createDefaultChatRoom()

        // then
        assertThat(chatRoom).isNotNull
        assertThat(chatRoom.workspaceId).isEqualTo(workspace.id)
        assertThat(chatRoom.chatRoomType).isEqualTo(ChatRoomType.DEFAULT)
    }
}
