package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.dto.request.ChatRoomCreateRequest
import com.backgu.amaker.chat.dto.response.ChatRoomResponse
import com.backgu.amaker.chat.dto.response.ChatRoomsViewResponse
import com.backgu.amaker.chat.service.ChatRoomFacadeService
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.common.infra.ApiHandler
import com.backgu.amaker.security.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/workspaces/{workspace-id}")
class ChatRoomController(
    private val chatRoomFacadeService: ChatRoomFacadeService,
    private val apiHandler: ApiHandler,
) : ChatRoomSwagger {
    @PostMapping("/chat-rooms")
    override fun createChatRoom(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @RequestBody @Valid chatRoomCreateRequest: ChatRoomCreateRequest,
    ): ResponseEntity<ApiResult<ChatRoomResponse>> {
        val chatRoom = chatRoomFacadeService.createChatRoom(token.id, workspaceId, chatRoomCreateRequest.toDto())
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(chatRoom.chatRoomId)
                    .toUri(),
            ).body(apiHandler.onSuccess(ChatRoomResponse.of(chatRoom)))
    }

    @GetMapping("/chat-rooms/joined")
    override fun findChatRoomsJoined(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<ChatRoomsViewResponse>> =
        ResponseEntity
            .ok()
            .body(
                apiHandler.onSuccess(
                    ChatRoomsViewResponse.of(
                        chatRoomFacadeService.findChatRoomsJoined(
                            token.id,
                            workspaceId,
                        ),
                    ),
                ),
            )
}
