package com.backgu.amaker.api.chat.dto

data class ChatQuery(
    val cursor: Long,
    val chatRoomId: Long,
    val size: Int,
)
