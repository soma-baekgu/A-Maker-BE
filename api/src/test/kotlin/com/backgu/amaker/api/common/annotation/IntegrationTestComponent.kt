package com.backgu.amaker.api.common.annotation

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Primary
@Component
annotation class IntegrationTestComponent
