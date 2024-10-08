package com.backgu.amaker.api.chat.controller

import com.backgu.amaker.api.chat.dto.request.ChatRoomCreateRequest
import com.backgu.amaker.api.chat.dto.response.BriefChatRoomResponse
import com.backgu.amaker.api.chat.dto.response.ChatRoomResponse
import com.backgu.amaker.api.chat.dto.response.ChatRoomUsersResponse
import com.backgu.amaker.api.chat.dto.response.ChatRoomsViewResponse
import com.backgu.amaker.api.chat.service.ChatRoomFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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
@RequestMapping("/api/v1")
class ChatRoomController(
    private val chatRoomFacadeService: ChatRoomFacadeService,
    private val apiHandler: ApiHandler,
) : ChatRoomSwagger {
    @PostMapping("/workspaces/{workspace-id}/chat-rooms")
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

    @GetMapping("/workspaces/{workspace-id}/chat-rooms")
    override fun findChatRooms(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<BriefChatRoomResponse>> =
        ResponseEntity
            .ok()
            .body(
                apiHandler.onSuccess(
                    BriefChatRoomResponse.of(
                        chatRoomFacadeService.findChatRooms(
                            token.id,
                            workspaceId,
                        ),
                    ),
                ),
            )

    @GetMapping("/workspaces/{workspace-id}/chat-rooms/not-joined")
    override fun findChatRoomsNotJoined(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<BriefChatRoomResponse>> =
        ResponseEntity
            .ok()
            .body(
                apiHandler.onSuccess(
                    BriefChatRoomResponse.of(
                        chatRoomFacadeService.findNotRegisteredChatRooms(
                            token.id,
                            workspaceId,
                        ),
                    ),
                ),
            )

    @GetMapping("/workspaces/{workspace-id}/chat-rooms/joined")
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

    @PostMapping("/workspaces/{workspace-id}/chat-rooms/{chat-room-id}/join")
    override fun joinChatRoom(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<Unit> {
        chatRoomFacadeService.joinChatRoom(token.id, workspaceId, chatRoomId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/chat-rooms/{chat-room-id}")
    override fun getChatRoom(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatRoomResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                ChatRoomResponse.of(
                    chatRoomFacadeService.getChatRoom(token.id, chatRoomId),
                ),
            ),
        )

    @GetMapping("/chat-rooms/{chat-room-id}/users")
    override fun getChatRoomUsers(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatRoomUsersResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                ChatRoomUsersResponse.of(
                    chatRoomFacadeService.getChatRoomUsers(token.id, chatRoomId),
                ),
            ),
        )
}
