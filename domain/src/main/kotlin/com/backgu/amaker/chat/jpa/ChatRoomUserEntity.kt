package com.backgu.amaker.chat.jpa

import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ChatRoomUser")
@Table(name = "chat_room_user")
class ChatRoomUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val chatRoomId: Long,
    @Column(nullable = true)
    var lastReadChatId: Long? = null,
) : BaseTimeEntity() {
    fun toDomain(): ChatRoomUser = ChatRoomUser(id = id, userId = userId, chatRoomId = chatRoomId, lastReadChatId = lastReadChatId)

    companion object {
        fun of(chatRoomUser: ChatRoomUser): ChatRoomUserEntity =
            ChatRoomUserEntity(
                id = chatRoomUser.id,
                userId = chatRoomUser.userId,
                chatRoomId = chatRoomUser.chatRoomId,
                lastReadChatId = chatRoomUser.lastReadChatId,
            )
    }
}
