package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.dto.request.GeneralChatCreateRequest
import com.backgu.amaker.chat.service.ChatFacadeService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatFacadeService: ChatFacadeService,
) {
    @MessageMapping("/chat-rooms/{chat-rooms-id}/general")
    fun sendGeneralChat(
        @Payload chat: GeneralChatCreateRequest,
        @DestinationVariable("chat-rooms-id") chatRoomId: Long,
    ) {
        chatFacadeService.createGeneralChat(chat.toDto(), chatRoomId)
    }
}
