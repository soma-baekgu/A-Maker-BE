package com.backgu.amaker.security.config

import com.backgu.amaker.security.JwtAuthenticationProvider
import com.backgu.amaker.security.filter.JwtAuthenticationTokenFilter
import com.backgu.amaker.security.handler.AuthAccessDeniedHandler
import com.backgu.amaker.security.jwt.component.JwtComponent
import com.backgu.amaker.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtSecurityConfig(
    private var jwtComponent: JwtComponent,
    private var userService: UserService,
) {
    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationTokenFilter = JwtAuthenticationTokenFilter(jwtComponent)

    @Bean
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider = JwtAuthenticationProvider(userService)

    @Bean
    fun jwtAccessDeniedHandler(): AuthAccessDeniedHandler = AuthAccessDeniedHandler()
}
