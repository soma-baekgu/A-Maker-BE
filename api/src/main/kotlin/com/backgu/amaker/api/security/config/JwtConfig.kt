package com.backgu.amaker.api.security.config

import com.backgu.amaker.common.security.jwt.config.JwtDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.Date

@Configuration
class JwtConfig : JwtDetails {
    @Value("\${jwt.issuer}")
    override lateinit var issuer: String

    @Value("\${jwt.client-secret}")
    override lateinit var clientSecret: String

    @Value("\${jwt.expiration:3600}")
    override var expiration: Int = 0

    override fun getExpirationMillis(current: Long): Long = current + expiration * 1000L

    override fun getExpirationDate(current: Date): Date = Date(getExpirationMillis(current.time))
}
