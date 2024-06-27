package com.backgu.amaker.security.jwt.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.Date

@Configuration
class JwtConfig {
    @Value("\${jwt.issuer}")
    lateinit var issuer: String

    @Value("\${jwt.client-secret}")
    lateinit var clientSecret: String

    @Value("\${jwt.expiration:3600}")
    var expiration: Int = 0

    fun getExpirationMillis(current: Long): Long = current + expiration * 1000L

    fun getExpirationDate(current: Date): Date = Date(getExpirationMillis(current.time))
}
