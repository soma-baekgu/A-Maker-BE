package com.backgu.amaker.api.fixture

import com.backgu.amaker.api.fixture.dto.ReactionCommentFixtureDto
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.chat.ChatType
import org.springframework.stereotype.Component

@Component
class ReactionCommentFixtureFacade(
    val reactionCommentFixture: ReactionCommentFixture,
    val reactionEventFixture: ReactionEventFixture,
    val reactionOptionFixture: ReactionOptionFixture,
    val chatRoomFixture: ChatRoomFixture,
    val chatFixture: ChatFixture,
    val eventAssignedUserFixture: EventAssignedUserFixture,
    val userFixture: UserFixture,
    private val workspaceFixture: WorkspaceFixture,
    private val workspaceUserFixture: WorkspaceUserFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
        workspaceId: Long = 1L,
    ): ReactionCommentFixtureDto {
        val workspace = workspaceFixture.createPersistedWorkspace(workspaceId, "test-workspace")
        workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, userId)
        userFixture.createPersistedUser(id = userId, name = name)
        val chatRoom = chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.DEFAULT)
        val chat = chatFixture.createPersistedChat(chatRoom.id, userId, chatType = ChatType.REPLY)
        val reactionEvent = reactionEventFixture.createPersistedReactionEvent(chat.id)
        val options = reactionOptionFixture.createPersistedReactionOptions(reactionEvent.id)
        eventAssignedUserFixture.createPersistedEventAssignedUser(userId, reactionEvent.id)
        return ReactionCommentFixtureDto(reactionEvent, options)
    }
}
