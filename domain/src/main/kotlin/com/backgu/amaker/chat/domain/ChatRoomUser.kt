package com.backgu.amaker.chat.domain

import com.backgu.amaker.common.domain.BaseTime

class ChatRoomUser(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
) : BaseTime()
