package com.backgu.amaker.user.domain

import com.backgu.amaker.workspace.domain.Workspace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("User 테스트")
class UserTest {
    @Test
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
}
