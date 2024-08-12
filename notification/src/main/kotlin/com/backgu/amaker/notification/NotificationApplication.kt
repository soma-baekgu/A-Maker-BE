package com.backgu.amaker.notification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@EntityScan(basePackages = ["com.backgu.amaker.infra"])
@EnableJpaRepositories(basePackages = ["com.backgu.amaker.infra.jpa.user", "com.backgu.amaker.infra.jpa.workspace"])
@EnableRedisRepositories(basePackages = ["com.backgu.amaker.infra.redis"])
@ComponentScan("com.backgu.amaker.notification", "com.backgu.amaker.infra.mail")
class NotificationApplication

fun main(args: Array<String>) {
    runApplication<NotificationApplication>(*args)
}
