package com.backgu.amaker.api.chat.controller

import com.backgu.amaker.api.chat.dto.request.ChatRoomCreateRequest
import com.backgu.amaker.api.chat.dto.response.BriefChatRoomResponse
import com.backgu.amaker.api.chat.dto.response.ChatRoomResponse
import com.backgu.amaker.api.chat.dto.response.ChatRoomUsersResponse
import com.backgu.amaker.api.chat.dto.response.ChatRoomsViewResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "chatRoom", description = "채팅방 API")
interface ChatRoomSwagger {
    @Operation(summary = "채팅방 생성", description = "채팅방을 생성한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅방 생성 성공",
            ),
        ],
    )
    fun createChatRoom(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @RequestBody chatRoomCreateRequest: ChatRoomCreateRequest,
    ): ResponseEntity<ApiResult<ChatRoomResponse>>

    @Operation(summary = "워크스페이스의 채팅방 조회", description = "워크스페이스의 채팅방을 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅방 조회 성공",
            ),
        ],
    )
    fun findChatRooms(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<BriefChatRoomResponse>>

    @Operation(summary = "가입하지 않은 채팅방 조회", description = "워크스페이스의 가입하지 않은 채팅방을 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅방 조회 성공",
            ),
        ],
    )
    fun findChatRoomsNotJoined(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<BriefChatRoomResponse>>

    @Operation(summary = "참여한 채팅방 조회", description = "사용자가 참여한 채팅방을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅방 조회 성공",
            ),
        ],
    )
    fun findChatRoomsJoined(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<ChatRoomsViewResponse>>

    @Operation(summary = "채팅방 참여", description = "사용자가 특정 채팅방에 참여합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "채팅방 참여 성공",
            ),
        ],
    )
    fun joinChatRoom(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<Unit>

    @Operation(summary = "단일 채팅방 조회", description = "특정 채팅방을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅방 조회 성공",
            ),
        ],
    )
    fun getChatRoom(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatRoomResponse>>

    @Operation(summary = "채팅방 참여 유저 조회", description = "특정 채팅방에 참여한 유저를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅방 유저 조회 성공",
            ),
        ],
    )
    fun getChatRoomUsers(
        token: JwtAuthentication,
        chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatRoomUsersResponse>>
}
