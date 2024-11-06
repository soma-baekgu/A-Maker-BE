package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.query.ReplyQueryRequest
import com.backgu.amaker.api.event.dto.query.TaskQueryRequest
import com.backgu.amaker.api.event.dto.request.ReactionCommentCreateRequest
import com.backgu.amaker.api.event.dto.request.ReplyCommentCreateRequest
import com.backgu.amaker.api.event.dto.request.TaskCommentCreateRequest
import com.backgu.amaker.api.event.dto.response.ReactionOptionWithCommentResponse
import com.backgu.amaker.api.event.dto.response.ReplyCommentWithUserResponse
import com.backgu.amaker.api.event.dto.response.TaskCommentWithUserResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.http.response.PageResponse
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

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

    @Operation(summary = "task 이벤트 응답 조회", description = "task 이벤트 응답 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "task 이벤트 응답 조회 성공",
            ),
        ],
    )
    fun findTaskComments(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @ModelAttribute taskQueryRequest: TaskQueryRequest,
    ): ResponseEntity<ApiResult<PageResponse<TaskCommentWithUserResponse>>>

    @Operation(summary = "reaction 이벤트 응답 조회", description = "reaction 이벤트 응답 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "reaction 이벤트 응답 조회 성공",
            ),
        ],
    )
    fun findReactionComments(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
    ): ResponseEntity<ApiResult<List<ReactionOptionWithCommentResponse>>>

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

    @Operation(summary = "task 이벤트 응답 생성", description = "task 이벤트 응답 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "task 이벤트 응답 생성 성공",
            ),
        ],
    )
    fun createTaskComment(
        token: JwtAuthentication,
        eventId: Long,
        taskCommentCreateRequest: TaskCommentCreateRequest,
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
