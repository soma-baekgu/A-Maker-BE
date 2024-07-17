package com.backgu.amaker.chat.domain

import java.time.LocalDateTime

class Chat(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
    var content: String,
    val chatType: ChatType,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
