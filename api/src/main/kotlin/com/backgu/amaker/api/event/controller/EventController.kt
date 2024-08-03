package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.api.event.dto.request.ReplyEventCreateRequest
import com.backgu.amaker.api.event.dto.response.ReplyEventDetailResponse
import com.backgu.amaker.api.event.service.EventFacadeService
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/chat-rooms/{chat-room-id}")
class EventController(
    private val eventFacadeService: EventFacadeService,
    private val apiHandler: ApiHandler,
) : EventSwagger {
    @PostMapping("/events/reply")
    override fun createReplyEvent(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @RequestBody @Valid request: ReplyEventCreateRequest,
    ): ResponseEntity<Unit> =
        ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/events/{id}/reply")
                    .buildAndExpand(
                        eventFacadeService
                            .createReplyEvent(
                                token.id,
                                chatRoomId,
                                request.toDto(),
                            ).id,
                    ).toUri(),
            ).build()

    @GetMapping("/events/{event-id}/reply")
    override fun getReplyEvent(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("chat-room-id") chatRoomId: Long,
        @PathVariable("event-id") eventId: Long,
    ): ResponseEntity<ApiResult<ReplyEventDetailResponse>> =
        ResponseEntity
            .ok()
            .body(
                apiHandler.onSuccess(
                    ReplyEventDetailResponse.of(
                        eventFacadeService.getReplyEvent(
                            token.id,
                            chatRoomId,
                            eventId,
                        ),
                    ),
                ),
            )
}
