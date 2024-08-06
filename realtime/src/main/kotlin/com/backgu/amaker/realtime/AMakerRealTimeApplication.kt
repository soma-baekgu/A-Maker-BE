package com.backgu.amaker.realtime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["com.backgu.amaker.infra"])
@EnableJpaRepositories(basePackages = ["com.backgu.amaker.infra"])
@SpringBootApplication(scanBasePackages = ["com.backgu.amaker.realtime", "com.backgu.amaker.infra.jpa"])
class AMakerRealTimeApplication

fun main(args: Array<String>) {
    runApplication<AMakerRealTimeApplication>(*args)
}
