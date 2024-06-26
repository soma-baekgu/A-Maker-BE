package com.backgu.amaker.chat.domain

import com.backgu.amaker.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "chat_room")
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val workspaceId: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val chatRoomType: ChatRoomType,
) : BaseTimeEntity()
