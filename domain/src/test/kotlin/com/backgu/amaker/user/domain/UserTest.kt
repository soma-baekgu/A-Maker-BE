package com.backgu.amaker.user.domain

import com.backgu.amaker.workspace.domain.Workspace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("User 테스트")
class UserTest {
    @Test
    @DisplayName("워크스페이스 생성 테스트")
    fun createWorkspace() {
        // given
        val user =
            User(id = "user1", name = "user1", email = "user1@gmail.com", picture = "/images/default_thumbnail.png")

        // when
        val workspace: Workspace = user.createWorkspace(name = "workspace1")

        // then
        assertThat(workspace).isNotNull
        assertThat(workspace.name).isEqualTo("workspace1")
    }

    @Test
    @DisplayName("초대받은 사용자가 아닌지 확인")
    fun isNonInvitee() {
        // given
        val leader = User(id = "me", name = "me", email = "me@gmail.com", picture = "/images/default_thumbnail.png")
        val invitee =
            User(
                id = "invitee",
                name = "invitee",
                email = "invitee@gmail.com",
                picture = "/images/default_thumbnail.png",
            )

        // when
        val result = leader.isNonInvitee(invitee)

        // then
        assertThat(result).isTrue
    }
}
