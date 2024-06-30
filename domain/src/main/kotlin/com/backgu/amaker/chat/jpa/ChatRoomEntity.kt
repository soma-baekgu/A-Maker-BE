package com.backgu.amaker.chat.jpa

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ChatRoom")
@Table(name = "chat_room")
class ChatRoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val workspaceId: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val chatRoomType: ChatRoomType,
) : BaseTimeEntity()
