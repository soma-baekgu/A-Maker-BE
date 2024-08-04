package com.backgu.amaker.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableAsync
@EntityScan(basePackages = ["com.backgu.amaker.infra"])
@EnableRedisRepositories(basePackages = ["com.backgu.amaker.infra.redis"])
@EnableJpaRepositories(basePackages = ["com.backgu.amaker.infra"])
@SpringBootApplication(scanBasePackages = ["com.backgu.amaker.api", "com.backgu.amaker.infra.jpa"])
class AMakerApiApplication

fun main(args: Array<String>) {
    runApplication<AMakerApiApplication>(*args)
}
