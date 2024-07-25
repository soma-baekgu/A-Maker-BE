package com.backgu.amaker.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "amazon")
class AWSConfig(
    var s3: S3Constants = S3Constants(),
) {
    class S3Constants {
        lateinit var accessKey: String
        lateinit var secretKey: String
        lateinit var region: String
        lateinit var bucket: String
        lateinit var url: String
        lateinit var locate: String
    }
}
