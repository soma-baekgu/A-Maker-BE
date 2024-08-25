package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod

open class ChatRoomNotification(
    val chatRoomId: Long,
    override val method: RealTimeNotificationMethod,
) : RealTimeBasedNotification(method) {
    override val keyPrefix: String
        get() = "CHAT_ROOM"

    override val keyValue: String
        get() = chatRoomId.toString()
}
