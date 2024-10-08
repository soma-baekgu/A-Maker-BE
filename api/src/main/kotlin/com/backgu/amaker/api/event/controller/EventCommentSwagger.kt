package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.query.ReplyQueryRequest
import com.backgu.amaker.api.event.dto.request.ReactionCommentCreateRequest
import com.backgu.amaker.api.event.dto.request.ReplyCommentCreateRequest
import com.backgu.amaker.api.event.dto.response.ReplyCommentWithUserResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.http.response.PageResponse
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "eventComment", description = "이벤트 응답 API")
interface EventCommentSwagger {
    @Operation(summary = "reply 이벤트 응답 조회", description = "reply 이벤트 응답 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "reply 이벤트 응답 조회 성공",
            ),
        ],
    )
    fun findReplyComments(
        token: JwtAuthentication,
        eventId: Long,
        replyQueryRequest: ReplyQueryRequest,
    ): ResponseEntity<ApiResult<PageResponse<ReplyCommentWithUserResponse>>>

    @Operation(summary = "reply 이벤트 응답 생성", description = "reply 이벤트 응답 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "reply 이벤트 응답 생성 성공",
            ),
        ],
    )
    fun createReplyComment(
        token: JwtAuthentication,
        eventId: Long,
        replyCommentCreateRequest: ReplyCommentCreateRequest,
    ): ResponseEntity<Unit>

    @Operation(summary = "reaction 이벤트 응답 생성", description = "reaction 이벤트 응답 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "reaction 이벤트 응답 생성 성공",
            ),
        ],
    )
    fun createReactionComment(
        token: JwtAuthentication,
        eventId: Long,
        reactionCommentCreateRequest: ReactionCommentCreateRequest,
    ): ResponseEntity<Unit>
}
