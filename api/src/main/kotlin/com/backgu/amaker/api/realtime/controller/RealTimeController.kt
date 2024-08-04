package com.backgu.amaker.api.realtime.controller

import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.api.realtime.dto.response.RealTimeServerResponse
import com.backgu.amaker.api.realtime.service.RealTimeFacadeService
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress

@RestController
@RequestMapping("/system/v1/realtime")
class RealTimeController(
    private val apiHandler: ApiHandler,
    private val realTimeFacadeService: RealTimeFacadeService,
) {
    @PostMapping
    fun connect(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestParam("open_port", required = true) serverPort: Int,
        clientAddress: InetAddress,
    ): ResponseEntity<ApiResult<RealTimeServerResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                RealTimeServerResponse.of(
                    realTimeFacadeService.connect(clientAddress, serverPort),
                ),
            ),
        )
}
