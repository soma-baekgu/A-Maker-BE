package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.response.EventBriefResponse
import com.backgu.amaker.api.event.service.EventFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import com.backgu.amaker.domain.event.EventStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Locale

@RestController
@RequestMapping("/api/v1/workspaces/{workspace-id}/events")
class EventQueryController(
    private val eventFacadeService: EventFacadeService,
    private val apiHandler: ApiHandler,
) : EventQuerySwagger {
    @GetMapping
    override fun getOngoingEvent(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @RequestParam status: String,
    ): ResponseEntity<ApiResult<List<EventBriefResponse>>> {
        val eventStatus = EventStatus.valueOf(status.uppercase(Locale.getDefault()))
        return ResponseEntity.ok().body(
            apiHandler.onSuccess(
                eventFacadeService
                    .getEvents(
                        token.id,
                        workspaceId,
                        eventStatus,
                    ).map { EventBriefResponse.of(it, token.id) },
            ),
        )
    }
}
