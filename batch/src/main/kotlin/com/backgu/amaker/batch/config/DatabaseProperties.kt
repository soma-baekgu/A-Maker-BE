package com.backgu.amaker.batch.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring")
class DatabaseProperties {
    lateinit var batchDatasource: DataSourceProperties
    lateinit var domainDatasource: DataSourceProperties

    class DataSourceProperties {
        lateinit var url: String
        lateinit var username: String
        lateinit var password: String
        lateinit var driverClassName: String
    }
}
