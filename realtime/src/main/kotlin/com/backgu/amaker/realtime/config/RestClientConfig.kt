package com.backgu.amaker.realtime.config

import com.backgu.amaker.common.http.RestClientAspect
import com.backgu.amaker.realtime.orchestration.config.OrchestrationConfig
import com.backgu.amaker.realtime.orchestration.infra.OrchestrationClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class RestClientConfig {
    @Bean
    fun restClientAspect() = RestClientAspect()

    @Bean
    fun orchestrationClient(orchestrationConfig: OrchestrationConfig): OrchestrationClient {
        val restClient = RestClient.builder().baseUrl(orchestrationConfig.baseUrl).build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(OrchestrationClient::class.java)
    }
}
