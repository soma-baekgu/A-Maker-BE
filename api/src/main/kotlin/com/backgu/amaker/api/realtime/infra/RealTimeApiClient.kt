package com.backgu.amaker.api.realtime.infra

import com.backgu.amaker.api.realtime.dto.response.RealTimeServerHealthResponse
import com.backgu.amaker.common.http.CaughtHttpExchange
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

@CaughtHttpExchange
interface RealTimeApiClient {
    @GetExchange("/actuator/health")
    fun healthCheck(
        @RequestHeader("Authorization") authorization: String,
    ): RealTimeServerHealthResponse?
}
