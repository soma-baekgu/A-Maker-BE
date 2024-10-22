package com.backgu.amaker.api.event.controller

import com.backgu.amaker.api.event.dto.response.EventBriefResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "event-query", description = "이벤트 조회 API")
interface EventQuerySwagger {
    @Operation(summary = "진행중인 이벤트 조회", description = "진행중인 이벤트 리스트 조회입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "진행중인 이벤트 리스트 조회 성공",
            ),
        ],
    )
    fun getOngoingEvent(
        @AuthenticationPrincipal token: JwtAuthentication,
        @PathVariable("workspace-id") workspaceId: Long,
        status: String,
    ): ResponseEntity<ApiResult<List<EventBriefResponse>>>
}
