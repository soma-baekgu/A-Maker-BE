package com.backgu.amaker.api.common.config

import com.backgu.amaker.common.clock.ClockHolder
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.id.IdPublisher
import com.backgu.amaker.infra.common.clock.SystemClockHolder
import com.backgu.amaker.infra.common.id.UUIDIdPublisher
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonAppConfig {
    @Bean
    fun clockHolder(): ClockHolder = SystemClockHolder()

    @Bean
    fun idPublisher(): IdPublisher = UUIDIdPublisher()

    @Bean
    fun apiHandler(
        request: HttpServletRequest,
        response: HttpServletResponse,
        clockHolder: ClockHolder,
    ): ApiHandler = ApiHandler(request, response, clockHolder)
}
