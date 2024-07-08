package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.annotation.ChattingLoginUser
import com.backgu.amaker.chat.dto.query.ChatQueryRequest
import com.backgu.amaker.chat.dto.request.GeneralChatCreateRequest
import com.backgu.amaker.chat.dto.response.ChatListResponse
import com.backgu.amaker.chat.dto.response.ChatResponse
import com.backgu.amaker.chat.service.ChatFacadeService
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.common.infra.ApiHandler
import com.backgu.amaker.security.JwtAuthentication
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ChatController(
    private val chatFacadeService: ChatFacadeService,
    private val apiHandler: ApiHandler,
) : ChatSwagger {
    @MessageMapping("/chat-rooms/{chat-rooms-id}/general")
    @SendTo("/sub/chat-rooms/{chat-rooms-id}")
    fun sendGeneralChat(
        @Payload generalChatCreateRequest: GeneralChatCreateRequest,
        @DestinationVariable("chat-rooms-id") chatRoomId: Long,
        @ChattingLoginUser token: JwtAuthentication,
    ): ChatResponse = ChatResponse.of(chatFacadeService.createGeneralChat(generalChatCreateRequest.toDto(), token.id, chatRoomId))

    @GetMapping("/chat-rooms/{chat-rooms-id}/chats")
    override fun getPreviousChat(
        @AuthenticationPrincipal token: JwtAuthentication,
        @ModelAttribute chatQueryRequest: ChatQueryRequest,
        @PathVariable("chat-rooms-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatListResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                ChatListResponse.of(
                    chatFacadeService.getPreviousChat(
                        token.id,
                        chatQueryRequest.toDto(chatRoomId),
                    ),
                ),
            ),
        )
}
