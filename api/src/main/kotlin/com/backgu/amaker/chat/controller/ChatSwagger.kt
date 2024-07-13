package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.dto.query.ChatQueryRequest
import com.backgu.amaker.chat.dto.response.ChatListResponse
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.security.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "chat", description = "채팅 API")
interface ChatSwagger {
    @Operation(summary = "채팅 조회", description = "커서 이전의 채팅 데이터를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "채팅 조회 성공",
            ),
        ],
    )
    fun getPreviousChat(
        token: JwtAuthentication,
        @ModelAttribute chatQueryRequest: ChatQueryRequest,
        @PathVariable("chat-rooms-id") chatRoomId: Long,
    ): ResponseEntity<ApiResult<ChatListResponse>>
}
