package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.request.ReactionEventCreateRequest
import com.backgu.amaker.api.event.dto.request.ReplyEventCreateRequest
import com.backgu.amaker.api.event.dto.response.ReactionEventDetailResponse
import com.backgu.amaker.api.event.dto.response.ReplyEventDetailResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "event", description = "이벤트 API")
interface EventSwagger {
    @Operation(summary = "reply 이벤트 상세조회", description = "reply 이벤트 상세조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "reply 이벤트 상세조회 성공",
            ),
        ],
    )
    fun getReplyEvent(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @PathVariable("event-id") eventId: Long,
    ): ResponseEntity<ApiResult<ReplyEventDetailResponse>>

    @Operation(summary = "reaction 이벤트 상세조회", description = "reaction 이벤트 상세조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "reaction 이벤트 상세조회 성공",
            ),
        ],
    )
    fun getReactionEvent(
        token: JwtAuthentication,
        chatRoomId: Long,
        eventId: Long,
    ): ResponseEntity<ApiResult<ReactionEventDetailResponse>>

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

    @Operation(summary = "reaction 이벤트 생성", description = "reaction 이벤트 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "reaction 이벤트 생성 성공",
            ),
        ],
    )
    @PostMapping("/events/reaction")
    fun createReactionEvent(
        token: JwtAuthentication,
        chatRoomId: Long,
        request: ReactionEventCreateRequest,
    ): ResponseEntity<Unit>
}
