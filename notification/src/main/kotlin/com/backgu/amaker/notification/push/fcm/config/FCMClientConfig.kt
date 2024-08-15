package com.backgu.amaker.notification.push.fcm.config

import com.backgu.amaker.notification.push.fcm.infra.FcmClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class FCMClientConfig(
    private val fcmConfig: FCMConfig,
) {
    @Bean
    fun fcmClient(): FcmClient {
        val fcmClient = RestClient.builder().baseUrl(fcmConfig.baseUrl).build()
        val adapter = RestClientAdapter.create(fcmClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(FcmClient::class.java)
    }
}
