package com.backgu.amaker.chat.domain

import com.backgu.amaker.common.domain.BaseTime
import java.util.UUID

class ChatRoomUser(
    val id: Long = 0L,
    val userId: UUID,
    val chatRoomId: Long,
) : BaseTime()
