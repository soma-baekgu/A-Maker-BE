package com.backgu.amaker.api.notification.controller

import com.backgu.amaker.api.event.dto.response.ReplyEventDetailResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "notification", description = "알림 API")
interface NotificationSwagger {
    @Operation(summary = "알림 리스트 조회", description = "알림 내역들을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "알림내역 조회 성공",
            ),
        ],
    )
    fun getNotifications(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
    ): ResponseEntity<ApiResult<ReplyEventDetailResponse>>
}
