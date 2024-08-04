package com.backgu.amaker.api.security.config

import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.api.security.handler.AuthAccessDeniedHandler
import com.backgu.amaker.common.security.jwt.component.JwtComponent
import com.backgu.amaker.common.security.jwt.utils.JwtAuthenticationTokenExtractor
import com.backgu.amaker.common.security.jwt.web.JwtAuthenticationTokenFilter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtSecurityConfig {
    @Bean
    fun jwtComponent(jwtConfig: JwtConfig): JwtComponent = JwtComponent(jwtConfig)

    @Bean
    fun jwtAuthenticationTokenExtractor(jwtComponent: JwtComponent): JwtAuthenticationTokenExtractor =
        JwtAuthenticationTokenExtractor(jwtComponent)

    @Bean
    fun jwtAuthenticationFilter(jwtAuthenticationTokenExtractor: JwtAuthenticationTokenExtractor): JwtAuthenticationTokenFilter =
        JwtAuthenticationTokenFilter(jwtAuthenticationTokenExtractor)

    @Bean
    fun jwtAccessDeniedHandler(
        apiHandler: ApiHandler,
        objectMapper: ObjectMapper,
    ): AuthAccessDeniedHandler = AuthAccessDeniedHandler(apiHandler, objectMapper)
}
