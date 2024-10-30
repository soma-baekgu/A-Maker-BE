package com.backgu.amaker.api.notification.controller

import com.backgu.amaker.api.notification.config.EventNotificationSwagger
import com.backgu.amaker.api.notification.dto.request.NotificationQueryRequest
import com.backgu.amaker.api.notification.dto.response.EventNotificationViewResponse
import com.backgu.amaker.api.notification.service.NotificationFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/workspaces/{workspace-id}/notifications")
class EventNotificationController(
    private val notificationFacadeService: NotificationFacadeService,
    private val apiHandler: ApiHandler,
) : EventNotificationSwagger {
    @GetMapping
    override fun getNotifications(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @ModelAttribute notificationQueryRequest: NotificationQueryRequest,
    ): ResponseEntity<ApiResult<EventNotificationViewResponse>> {
        val pageable =
            PageRequest.of(
                notificationQueryRequest.page,
                notificationQueryRequest.size,
                Sort.by("id").ascending(),
            )

        return ResponseEntity.ok(
            apiHandler.onSuccess(
                EventNotificationViewResponse.of(
                    notificationFacadeService.getNotifications(
                        token.id,
                        workspaceId,
                        pageable,
                    ),
                ),
            ),
        )
    }
}
