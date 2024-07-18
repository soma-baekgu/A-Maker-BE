package com.backgu.amaker.chat.controller

import com.backgu.amaker.chat.dto.request.ChatRoomCreateRequest
import com.backgu.amaker.chat.dto.response.ChatRoomResponse
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.security.JwtAuthentication
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
        @PathVariable("w-id") workspaceId: Long,
        @RequestBody chatRoomCreateRequest: ChatRoomCreateRequest,
    ): ResponseEntity<ApiResult<ChatRoomResponse>>
}
