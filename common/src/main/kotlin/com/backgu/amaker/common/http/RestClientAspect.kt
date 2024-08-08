package com.backgu.amaker.common.http

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class RestClientAspect {
    @Pointcut("within(@com.backgu.amaker.common.http.CaughtHttpExchange *)")
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
