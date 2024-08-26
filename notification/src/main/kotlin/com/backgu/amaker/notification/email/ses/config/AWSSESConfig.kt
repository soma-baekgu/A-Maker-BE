package com.backgu.amaker.notification.email.ses.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ses.SesClient

@Configuration
@ConfigurationProperties(prefix = "amazon.ses")
class AWSSESConfig {
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var region: String
    lateinit var sender: String

    @Bean
    fun sesClient(): SesClient =
        SesClient
            .builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build()
}
