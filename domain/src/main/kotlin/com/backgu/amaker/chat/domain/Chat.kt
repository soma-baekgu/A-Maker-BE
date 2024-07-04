package com.backgu.amaker.chat.domain

import com.backgu.amaker.common.domain.BaseTime
import java.time.LocalDateTime

class Chat(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
    var content: String,
    val chatType: ChatType,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : BaseTime(createdAt, updatedAt)
