package com.backgu.amaker.api.config

import com.backgu.amaker.api.auth.config.AuthConfig
import com.backgu.amaker.api.auth.infra.GoogleApiClient
import com.backgu.amaker.api.auth.infra.GoogleOAuthClient
import com.backgu.amaker.common.http.RestClientAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class RestClientConfig(
    val authConfig: AuthConfig,
) {
    @Bean
    fun restClientAspect() = RestClientAspect()

    @Bean
    fun googleOauth2Service(): GoogleOAuthClient {
        val restClient = RestClient.builder().baseUrl(authConfig.oauthUrl).build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(GoogleOAuthClient::class.java)
    }

    @Bean
    fun googleApiService(): GoogleApiClient {
        val restClient = RestClient.builder().baseUrl(authConfig.apiUrl).build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(GoogleApiClient::class.java)
    }
}
