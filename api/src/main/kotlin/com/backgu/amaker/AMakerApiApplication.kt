package com.backgu.amaker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@EntityScan(basePackages = ["com.backgu.amaker"])
class AMakerApiApplication

fun main(args: Array<String>) {
    runApplication<AMakerApiApplication>(*args)
}
