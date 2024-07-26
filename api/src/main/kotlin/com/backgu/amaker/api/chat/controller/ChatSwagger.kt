package com.backgu.amaker.chat.controller

import com.backgu.amaker.api.chat.dto.query.ChatQueryRequest
import com.backgu.amaker.api.chat.dto.request.ChatCreateRequest
import com.backgu.amaker.api.chat.dto.response.ChatListResponse
import com.backgu.amaker.api.chat.dto.response.ChatWithUserResponse
import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.api.security.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "chat", description = "채팅 API")
interface ChatSwagger {
    @Operation(summary = "최근 단일 채팅 조회", description = "채팅방에 대해 가장 최근에 읽은 채팅 데이터를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "최근 채팅 조회 성공",
            ),
        ],
    )
    fun getChat(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatWithUserResponse<*>>>

    @Operation(summary = "커서 이후 채팅 조회", description = "커서 이전의 채팅 데이터를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "커서 이후 채팅 조회 성공",
            ),
        ],
    )
    fun getPreviousChat(
        @Parameter(hidden = true) token: JwtAuthentication,
        @ModelAttribute chatQueryRequest: ChatQueryRequest,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatListResponse>>

    @Operation(summary = "커서 이전 채팅 조회", description = "커서 이후의 채팅 데이터를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "커서 이후 채팅 조회 성공",
            ),
        ],
    )
    fun getAfterChat(
        @Parameter(hidden = true) token: JwtAuthentication,
        @ModelAttribute chatQueryRequest: ChatQueryRequest,
        @PathVariable("chat-room-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatListResponse>>

    @Operation(summary = "채팅 생성", description = "채팅을 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "채팅 생성 성공",
            ),
        ],
    )
    fun createChat(
        @Parameter(hidden = true) token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @RequestBody chatCreateRequest: ChatCreateRequest,
    ): ResponseEntity<Unit>

    @Operation(summary = "파일 채팅 생성", description = "파일 채팅을 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "파일 채팅 생성 성공",
            ),
        ],
    )
    fun createChatWithFile(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @Valid @org.springframework.web.bind.annotation.RequestBody fileChatCreateRequest: FileChatCreateRequest,
    ): ResponseEntity<Unit>
}
