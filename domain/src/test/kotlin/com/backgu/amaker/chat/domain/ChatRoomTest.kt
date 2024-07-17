package com.backgu.amaker.chat.domain

import com.backgu.amaker.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ChatRoom 테스트")
class ChatRoomTest {
    @Test
    @DisplayName("채팅방에 사용자를 추가할 수 있다")
    fun addUser() {
        // given
        val chatRoom = ChatRoom(workspaceId = 1, chatRoomType = ChatRoomType.GROUP)
        val user1 =
            User(id = "user1", name = "user1", email = "user1@gmail.com", picture = "/images/default_thumbnail.png")

        // when
        val chatRoomUser: ChatRoomUser = chatRoom.addUser(user = user1)

        // then
        assertThat(chatRoomUser).isNotNull
        assertThat(chatRoomUser.userId).isEqualTo("user1")
        assertThat(chatRoomUser.chatRoomId).isEqualTo(chatRoom.id)
    }

    @Test
    @DisplayName("채팅방에 일반 채팅을 생성할 수 있다")
    fun createGeneralChat() {
        // given
        val user =
            User(id = "user1", name = "user1", email = "user1@gmail.com", picture = "/images/default_thumbnail.png")
        val chatRoom = ChatRoom(workspaceId = 1, chatRoomType = ChatRoomType.GROUP)
        val content = "안녕하세요"

        // when
        val chat: Chat = chatRoom.createChat(user = user, chatRoom = chatRoom, content = content)

        // then
        assertThat(chat.chatType).isEqualTo(ChatType.GENERAL)
        assertThat(chat.content).isEqualTo(content)
    }

    @Test
    @DisplayName("채팅방의 마지막 채팅을 업데이트할 수 있다")
    fun updateLastChatId() {
        // given
        val chatRoom = ChatRoom(workspaceId = 1, chatRoomType = ChatRoomType.GROUP)
        val chat =
            Chat(
                userId = "user1",
                chatRoomId = chatRoom.id,
                content = "안녕하세요",
                chatType = ChatType.GENERAL,
            )

        // when
        val updatedChatRoom: ChatRoom = chatRoom.updateLastChatId(chat = chat)

        // then
        assertThat(updatedChatRoom.lastChatId).isEqualTo(chat.id)
    }
}
