package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.event.ReplyEvent
import org.springframework.stereotype.Component

@Component
class ReplyCommentFixtureFacade(
    val replyCommentFixture: ReplyCommentFixture,
    val replyEventFixture: ReplyEventFixture,
    val chatRoomFixture: ChatRoomFixture,
    val chatFixture: ChatFixture,
    val eventAssignedUserFixture: EventAssignedUserFixture,
    val userFixture: UserFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
    ): ReplyEvent {
        val user = userFixture.createPersistedUser(id = userId, name = name)
        val chatRoom = chatRoomFixture.createPersistedChatRoom(1, ChatRoomType.DEFAULT)
        val chat = chatFixture.createPersistedChat(chatRoom.id, userId, chatType = ChatType.REPLY)
        val replyEvent = replyEventFixture.createPersistedReplyEvent(chat.id)
        val eventAssignedUser = eventAssignedUserFixture.createPersistedEventAssignedUser(userId, replyEvent.id)
        return replyEvent
    }
}
