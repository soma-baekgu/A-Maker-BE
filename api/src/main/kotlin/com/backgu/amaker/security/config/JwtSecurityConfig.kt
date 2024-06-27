package com.backgu.amaker.security.config

import com.backgu.amaker.security.JwtAccessDeniedHandler
import com.backgu.amaker.security.JwtAuthenticationProvider
import com.backgu.amaker.security.JwtAuthenticationTokenFilter
import com.backgu.amaker.security.jwt.service.JwtService
import com.backgu.amaker.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtSecurityConfig(
    private var jwtService: JwtService,
    private var userService: UserService,
) {
    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationTokenFilter = JwtAuthenticationTokenFilter(jwtService)

    @Bean
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider = JwtAuthenticationProvider(userService)

    @Bean
    fun jwtAccessDeniedHandler(): JwtAccessDeniedHandler = JwtAccessDeniedHandler()
}
