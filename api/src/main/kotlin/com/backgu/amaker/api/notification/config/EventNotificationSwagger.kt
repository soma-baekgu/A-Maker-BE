package com.backgu.amaker.api.notification.config

import com.backgu.amaker.api.notification.dto.request.NotificationQueryRequest
import com.backgu.amaker.api.notification.dto.response.EventNotificationViewResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

interface EventNotificationSwagger {
    @Operation(summary = "이벤트 알림 조회", description = "이벤트 알림을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "이벤트 알림 리스트 성공",
            ),
        ],
    )
    fun getNotifications(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        @ModelAttribute notificationQueryRequest: NotificationQueryRequest,
    ): ResponseEntity<ApiResult<EventNotificationViewResponse>>
}
