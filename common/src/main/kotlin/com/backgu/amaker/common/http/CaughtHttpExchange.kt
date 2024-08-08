package com.backgu.amaker.common.http

import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CaughtHttpExchange
