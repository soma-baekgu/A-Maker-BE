package com.backgu.amaker.security.jwt.component

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.backgu.amaker.security.jwt.config.JwtConfig
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID

@Component
class JwtComponent(
    private var jwtConfig: JwtConfig,
) {
    private val jwtHashAlgorithm: Algorithm = Algorithm.HMAC256(jwtConfig.clientSecret)
    private val jwtVerifier = JWT.require(jwtHashAlgorithm).withIssuer(jwtConfig.issuer).build()

    fun create(
        userId: UUID,
        role: String,
    ): String = this.create(Claims.of(userId, role))

    fun create(claims: Claims): String {
        val now = Date()
        val builder: JWTCreator.Builder = JWT.create()
        builder.withIssuer(jwtConfig.issuer)
        builder.withIssuedAt(now)
        builder.withExpiresAt(jwtConfig.getExpirationDate(now))
        builder.withClaim("id", claims.id.toString())
        builder.withArrayClaim("roles", claims.roles)
        return builder.sign(jwtHashAlgorithm)
    }

    fun verify(token: String?): Claims = Claims(jwtVerifier.verify(token))

    class Claims {
        lateinit var id: String
        lateinit var roles: Array<String>
        lateinit var issuedAt: Date
        lateinit var expiresAt: Date

        private constructor()

        internal constructor(decodedJWT: DecodedJWT) {
            this.id = decodedJWT.getClaim("id").toString()
            this.roles = decodedJWT.getClaim("roles").asArray(String::class.java) ?: arrayOf()
            this.issuedAt = decodedJWT.issuedAt
            this.expiresAt = decodedJWT.expiresAt
        }

        companion object {
            fun of(
                id: UUID,
                role: String,
            ): Claims {
                val claims = Claims()
                claims.id = id.toString()
                claims.roles = arrayOf(role)
                return claims
            }
        }
    }
}
