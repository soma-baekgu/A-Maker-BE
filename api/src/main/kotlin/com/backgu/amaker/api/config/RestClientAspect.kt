package com.backgu.amaker.api.config

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class RestClientAspect {
    @Pointcut("within(@CaughtHttpExchange *)")
    fun caughtHttpExchange() {
    }

    @Around("caughtHttpExchange()")
    fun handleException(jointPoint: ProceedingJoinPoint): Any? =
        try {
            jointPoint.proceed()
        } catch (e: Exception) {
            null
        }
}
