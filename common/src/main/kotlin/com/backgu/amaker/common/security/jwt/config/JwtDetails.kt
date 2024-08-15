package com.backgu.amaker.common.security.jwt.config

import java.util.Date

interface JwtDetails {
    var issuer: String
    var clientSecret: String
    var expiration: Int

    fun getExpirationMillis(current: Long): Long = current + expiration * 1000L

    fun getExpirationDate(current: Date): Date = Date(getExpirationMillis(current.time))
}
