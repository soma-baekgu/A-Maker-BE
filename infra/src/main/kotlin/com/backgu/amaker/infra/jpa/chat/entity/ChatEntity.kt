package com.backgu.amaker.infra.jpa.chat.entity

import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "Chat")
@Table(name = "chat")
class ChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val chatRoomId: Long,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    var content: String,
    @Enumerated(EnumType.STRING)
    val chatType: ChatType,
) : BaseTimeEntity() {
    fun toDomain(): Chat =
        Chat(
            id = id,
            chatRoomId = chatRoomId,
            userId = userId,
            content = content,
            chatType = chatType,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(chat: Chat) =
            ChatEntity(
                chatRoomId = chat.chatRoomId,
                userId = chat.userId,
                content = chat.content,
                chatType = chat.chatType,
            )
    }
}
