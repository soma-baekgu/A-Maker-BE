package com.backgu.amaker.domain.notifiacation.chat

import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.notifiacation.ChatRoomNotification
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.domain.user.User

class NewChat(
    chatRoom: ChatRoom,
    method: TemplateEmailNotificationMethod,
) : ChatRoomNotification(
        chatRoom.id,
        method,
    ) {
    companion object {
        private fun buildDetailMessage(
            chatRoom: ChatRoom,
            chat: Chat,
        ): String =
            "${chatRoom.name}에 새로운 메시지가 도착했습니다.\n" +
                "메시지 내용: ${chat.content}"

        fun of(
            sender: User,
            chat: Chat,
            chatRoom: ChatRoom,
        ): NewChat =
            NewChat(
                chatRoom,
                TemplateEmailNotificationMethod(
                    "${sender.name}님이 보낸 메시지",
                    chat.content,
                    buildDetailMessage(chatRoom, chat),
                    "chat-notification",
                ),
            )
    }
}
