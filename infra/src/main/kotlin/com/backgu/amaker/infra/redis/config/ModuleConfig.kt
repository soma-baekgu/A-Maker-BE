package com.backgu.amaker.infra.redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModuleConfig {
    @Bean
    fun timeObjectMapper(): ObjectMapper =
        jacksonObjectMapper()
            .registerModules(JavaTimeModule())
}
