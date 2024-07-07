package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.dto.request.GeneralChatCreateRequest
import com.backgu.amaker.chat.dto.response.ChatResponse
import com.backgu.amaker.chat.service.ChatFacadeService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatFacadeService: ChatFacadeService,
) {
    @MessageMapping("/chat-rooms/{chat-rooms-id}/general")
    @SendTo("/sub/chat-rooms/{chat-rooms-id}")
    fun sendGeneralChat(
        @Payload generalChatCreateRequest: GeneralChatCreateRequest,
        @DestinationVariable("chat-rooms-id") chatRoomId: Long,
    ): ChatResponse = ChatResponse.of(chatFacadeService.createGeneralChat(generalChatCreateRequest.toDto(), chatRoomId))
}
