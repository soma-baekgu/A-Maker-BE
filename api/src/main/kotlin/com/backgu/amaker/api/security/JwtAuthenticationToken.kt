package com.backgu.amaker.api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken : AbstractAuthenticationToken {
    private val principal: JwtAuthentication

    constructor(principal: JwtAuthentication) : super(null) {
        super.setAuthenticated(false)
        this.principal = principal
    }

    internal constructor(principal: JwtAuthentication, authorities: Collection<GrantedAuthority>) : super(
        authorities,
    ) {
        super.setAuthenticated(true)
        this.principal = principal
    }

    override fun getPrincipal(): JwtAuthentication = principal

    override fun getCredentials(): String = ""

    override fun setAuthenticated(isAuthenticated: Boolean) {
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
    }
}
