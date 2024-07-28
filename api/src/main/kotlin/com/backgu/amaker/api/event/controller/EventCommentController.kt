package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.request.ReplyCommentCreateRequest
import com.backgu.amaker.api.event.service.EventCommentFacadeService
import com.backgu.amaker.api.security.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1")
class EventCommentController(
    private val eventCommentFacadeService: EventCommentFacadeService,
) : EventCommentSwagger {
    @PostMapping("/events/{event-id}/reply/comments")
    override fun createReplyComment(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("event-id") eventId: Long,
        @RequestBody @Valid replyCommentCreateRequest: ReplyCommentCreateRequest,
    ): ResponseEntity<Unit> =
        ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(
                        eventCommentFacadeService
                            .createReplyComment(
                                token.id,
                                eventId,
                                replyCommentCreateRequest.toDto(),
                            ).id,
                    ).toUri(),
            ).build()
}
