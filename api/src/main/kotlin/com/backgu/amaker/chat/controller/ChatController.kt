package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.annotation.ChattingLoginUser
import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.dto.ChatWithUserDto
import com.backgu.amaker.chat.dto.query.ChatQueryRequest
import com.backgu.amaker.chat.dto.request.ChatCreateRequest
import com.backgu.amaker.chat.dto.request.FileChatCreateRequest
import com.backgu.amaker.chat.dto.response.ChatListResponse
import com.backgu.amaker.chat.dto.response.ChatWithUserResponse
import com.backgu.amaker.chat.service.ChatFacadeService
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.common.infra.ApiHandler
import com.backgu.amaker.security.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1")
class ChatController(
    private val chatFacadeService: ChatFacadeService,
    private val apiHandler: ApiHandler,
) : ChatSwagger {
    @Deprecated("HTTP API로 대체", ReplaceWith("createChat"))
    @MessageMapping("/chat-rooms/{chat-rooms-id}/general")
    @SendTo("/sub/chat-rooms/{chat-rooms-id}")
    fun sendGeneralChat(
        @Payload chatCreateRequest: ChatCreateRequest,
        @DestinationVariable("chat-rooms-id") chatRoomId: Long,
        @ChattingLoginUser token: JwtAuthentication,
    ): ChatWithUserResponse = ChatWithUserResponse.of(chatFacadeService.createChat(chatCreateRequest.toDto(), token.id, chatRoomId))

    @GetMapping("/chat-rooms/{chat-room-id}/chats/recent")
    override fun getChat(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatWithUserResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                ChatWithUserResponse.of(
                    chatFacadeService.getRecentChat(
                        token.id,
                        chatRoomId,
                    ),
                ),
            ),
        )

    @GetMapping("/chat-rooms/{chat-room-id}/chats/previous")
    override fun getPreviousChat(
        @AuthenticationPrincipal token: JwtAuthentication,
        @ModelAttribute chatQueryRequest: ChatQueryRequest,
        @PathVariable("chat-room-id") chatRoomId: Long,
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

    @GetMapping("/chat-rooms/{chat-room-id}/chats/after")
    override fun getAfterChat(
        @AuthenticationPrincipal token: JwtAuthentication,
        @ModelAttribute chatQueryRequest: ChatQueryRequest,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatListResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                ChatListResponse.of(
                    chatFacadeService.getAfterChat(
                        token.id,
                        chatQueryRequest.toDto(chatRoomId),
                    ),
                ),
            ),
        )

    @PostMapping("/chat-rooms/{chat-room-id}/chats")
    override fun createChat(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @Valid @RequestBody chatCreateRequest: ChatCreateRequest,
    ): ResponseEntity<Unit> {
        val chatWithUserDto: ChatWithUserDto =
            chatFacadeService.createChat(chatCreateRequest.toDto(), token.id, chatRoomId)
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(chatWithUserDto.id)
                    .toUri(),
            ).build()
    }

    @PostMapping("/chat-rooms/{chat-room-id}/chats/file")
    override fun createChatWithFile(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @Valid @RequestBody fileChatCreateRequest: FileChatCreateRequest,
    ): ResponseEntity<Unit> {
        val chatWithUserDto: ChatWithUserDto =
            chatFacadeService.createChat(fileChatCreateRequest.toDto(), token.id, chatRoomId, ChatType.FILE)
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(chatWithUserDto.id)
                    .toUri(),
            ).build()
    }
}
