package com.backgu.amaker.chat.dto

data class ChatQuery(
    val cursor: Long,
    val chatRoomId: Long,
    val size: Int,
)
