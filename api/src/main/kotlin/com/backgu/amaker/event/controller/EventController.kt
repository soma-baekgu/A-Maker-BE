package com.backgu.amaker.event.controller

import com.backgu.amaker.event.dto.request.ReplyEventCreateRequest
import com.backgu.amaker.event.service.EventFacadeService
import com.backgu.amaker.security.JwtAuthentication
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
class EventController(
    private val eventFacadeService: EventFacadeService,
) : EventSwagger {
    @PostMapping("/chat-rooms/{chat-room-id}/events/reply")
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
}
