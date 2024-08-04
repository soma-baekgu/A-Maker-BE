package com.backgu.amaker.realtime.orchestration.infra

import com.backgu.amaker.common.http.CaughtHttpExchange
import com.backgu.amaker.common.http.response.ApiSuccess
import com.backgu.amaker.realtime.orchestration.config.OrchestrationConfig
import com.backgu.amaker.realtime.orchestration.dto.ServerInformation
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.PostExchange

@CaughtHttpExchange
interface OrchestrationClient {
    @PostExchange(OrchestrationConfig.END_POINT)
    fun registerThisServer(
        @RequestHeader("Authorization") authorization: String,
        @RequestParam("open_port") openPort: Int,
    ): ApiSuccess<ServerInformation>?
}
