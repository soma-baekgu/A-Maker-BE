package com.backgu.amaker.api.security.config

import com.backgu.amaker.api.security.handler.AuthAccessDeniedHandler
import com.backgu.amaker.api.security.handler.AuthEntryPoint
import com.backgu.amaker.common.security.jwt.web.JwtAuthenticationTokenFilter
import com.backgu.amaker.domain.user.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationTokenFilter: JwtAuthenticationTokenFilter,
    private val authAccessDeniedHandler: AuthAccessDeniedHandler,
    private val authEntryPoint: AuthEntryPoint,
    private val corsConfig: CorsConfig,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {
                it.configurationSource(corsConfigurationSource())
            }.csrf {
                it.disable()
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.authorizeHttpRequests {
                it
                    .requestMatchers("/actuator/**")
                    .hasRole(UserRole.ADMIN.value)
                    .requestMatchers("/system/v1/**")
                    .hasRole(UserRole.ADMIN.value)
                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers(
                        "/auth/**",
                        "/api/v1/auth/**",
                        "/error",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/ws/**",
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(jwtAuthenticationTokenFilter, ExceptionTranslationFilter::class.java)
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authEntryPoint)
                it.accessDeniedHandler(authAccessDeniedHandler)
            }.httpBasic {
                it.disable()
            }.anonymous {
                it.disable()
            }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = corsConfig.allowedOrigins
        config.allowedMethods = corsConfig.allowedMethods
        config.allowedHeaders = corsConfig.allowedHeaders
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)
        return source
    }
}
