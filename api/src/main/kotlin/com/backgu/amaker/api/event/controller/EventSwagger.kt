package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.request.ReplyEventCreateRequest
import com.backgu.amaker.api.security.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "event", description = "이벤트 API")
interface EventSwagger {
    @Operation(summary = "reply 이벤트 생성", description = "reply 이벤트 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "reply 이벤트 생성 성공",
            ),
        ],
    )
    fun createReplyEvent(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @RequestBody @Valid request: ReplyEventCreateRequest,
    ): ResponseEntity<Unit>
}
