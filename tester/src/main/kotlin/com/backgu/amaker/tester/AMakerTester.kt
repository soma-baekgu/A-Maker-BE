package com.backgu.amaker.tester

import com.backgu.amaker.tester.setup.service.ChatSetupService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EntityScan(basePackages = ["com.backgu.amaker.infra"])
@EnableRedisRepositories(basePackages = ["com.backgu.amaker.infra.redis"])
@EnableJpaRepositories(basePackages = ["com.backgu.amaker.infra"])
@SpringBootApplication(
    scanBasePackages = [
        "com.backgu.amaker.tester",
        "com.backgu.amaker.infra.jpa",
        "com.backgu.amaker.infra.redis.chat",
        "com.backgu.amaker.infra.redis.user",
    ],
)
class AMakerTester(
    val chatSetupService: ChatSetupService,
) {
    fun setupchat() {
        chatSetupService.chatSetup()
    }

    @Bean
    fun run(): CommandLineRunner =
        CommandLineRunner {
            println("Running setupChat from CommandLineRunner...")
            setupchat()
        }
}

fun main(args: Array<String>) {
    runApplication<AMakerTester>(*args)
}
