package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.api.common.dto.response.PageResponse
import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.api.event.dto.query.ReplyQueryRequest
import com.backgu.amaker.api.event.dto.request.ReplyCommentCreateRequest
import com.backgu.amaker.api.event.dto.response.ReplyCommentWithUserResponse
import com.backgu.amaker.api.event.dto.response.ReplyCommentsViewResponse
import com.backgu.amaker.api.event.service.EventCommentFacadeService
import com.backgu.amaker.api.security.JwtAuthentication
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
    @PostMapping("/events/{event-id}/reply/comments")
    override fun createReplyComment(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @RequestBody @Valid replyCommentCreateRequest: ReplyCommentCreateRequest,
    ): ResponseEntity<Unit> {
        eventCommentFacadeService.createReplyComment(token.id, eventId, replyCommentCreateRequest.toDto())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

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
}
