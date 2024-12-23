package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.query.ReplyQueryRequest
import com.backgu.amaker.api.event.dto.query.TaskQueryRequest
import com.backgu.amaker.api.event.dto.request.ReactionCommentCreateRequest
import com.backgu.amaker.api.event.dto.request.ReplyCommentCreateRequest
import com.backgu.amaker.api.event.dto.request.TaskCommentCreateRequest
import com.backgu.amaker.api.event.dto.response.ReactionOptionWithCommentResponse
import com.backgu.amaker.api.event.dto.response.ReplyCommentWithUserResponse
import com.backgu.amaker.api.event.dto.response.ReplyCommentsViewResponse
import com.backgu.amaker.api.event.dto.response.TaskCommentWithUserResponse
import com.backgu.amaker.api.event.dto.response.TaskCommentsViewResponse
import com.backgu.amaker.api.event.service.EventCommentFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.http.response.PageResponse
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EventCommentController(
    private val eventCommentFacadeService: EventCommentFacadeService,
    private val apiHandler: ApiHandler,
) : EventCommentSwagger {
    @GetMapping("/events/{event-id}/reply/comments")
    override fun findReplyComments(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @ModelAttribute replyQueryRequest: ReplyQueryRequest,
    ): ResponseEntity<ApiResult<PageResponse<ReplyCommentWithUserResponse>>> {
        val pageable =
            PageRequest.of(
                replyQueryRequest.page,
                replyQueryRequest.size,
                Sort.by("id").ascending(),
            )
        return ResponseEntity.ok(
            apiHandler.onSuccess(
                ReplyCommentsViewResponse.of(
                    eventCommentFacadeService.findReplyComments(
                        token.id,
                        eventId,
                        pageable,
                    ),
                ),
            ),
        )
    }

    @GetMapping("/events/{event-id}/task/comments")
    override fun findTaskComments(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @ModelAttribute taskQueryRequest: TaskQueryRequest,
    ): ResponseEntity<ApiResult<PageResponse<TaskCommentWithUserResponse>>> {
        val pageable =
            PageRequest.of(
                taskQueryRequest.page,
                taskQueryRequest.size,
                Sort.by("id").ascending(),
            )
        return ResponseEntity.ok(
            apiHandler.onSuccess(
                TaskCommentsViewResponse.of(
                    eventCommentFacadeService.findTaskComments(
                        token.id,
                        eventId,
                        pageable,
                    ),
                ),
            ),
        )
    }

    @GetMapping("/events/{event-id}/reaction/comments")
    override fun findReactionComments(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
    ): ResponseEntity<ApiResult<List<ReactionOptionWithCommentResponse>>> =
        ResponseEntity.ok(
            apiHandler.onSuccess(
                eventCommentFacadeService
                    .findReactionComment(token.id, eventId)
                    .map { ReactionOptionWithCommentResponse.of(it) },
            ),
        )

    @PostMapping("/events/{event-id}/reply/comments")
    override fun createReplyComment(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @RequestBody @Valid replyCommentCreateRequest: ReplyCommentCreateRequest,
    ): ResponseEntity<Unit> {
        eventCommentFacadeService.createReplyComment(token.id, eventId, replyCommentCreateRequest.toDto())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/events/{event-id}/reaction/comments")
    override fun createReactionComment(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @RequestBody @Valid reactionCommentCreateRequest: ReactionCommentCreateRequest,
    ): ResponseEntity<Unit> {
        eventCommentFacadeService.createReactionComment(token.id, eventId, reactionCommentCreateRequest.toDto())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/events/{event-id}/task/comments")
    override fun createTaskComment(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @RequestBody @Valid taskCommentCreateRequest: TaskCommentCreateRequest,
    ): ResponseEntity<Unit> {
        eventCommentFacadeService.createTaskComment(token.id, eventId, taskCommentCreateRequest.toDto())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
