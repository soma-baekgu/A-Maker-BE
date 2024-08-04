package com.backgu.amaker.api.common.aop

import com.backgu.amaker.api.common.annotation.DistributedLock
import com.backgu.amaker.api.common.annotation.DistributedLockKey
import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.service.LockService
import com.backgu.amaker.common.status.StatusCode
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
class DistributedLockAspect(
    private val lockService: LockService,
) {
    @Pointcut("@annotation(distributedLock)")
    fun distributedLockExchange(distributedLock: DistributedLock) {
    }

    @Around("distributedLockExchange(distributedLock)")
    fun handleDistributedLock(
        joinPoint: ProceedingJoinPoint,
        distributedLock: DistributedLock,
    ): Any? {
        val methodSignature: MethodSignature = joinPoint.signature as MethodSignature
        val method: Method = methodSignature.method
        val paramsAnnotations: Array<Array<Annotation>> = method.parameterAnnotations
        val parameterNames: Array<String> = methodSignature.parameterNames
        val args: Array<Any> = joinPoint.args

        val lockParameters =
            paramsAnnotations
                .mapIndexedNotNull { index, annotations ->
                    if (annotations.any { it is DistributedLockKey }) {
                        parameterNames[index] to args[index]
                    } else {
                        null
                    }
                }.toMap()
        val lockKey = lockParameters.entries.joinToString(":") { (key, value) -> "$key:$value" }

        if (!lockService.tryLock(
                distributedLock.domain,
                lockKey,
            )
        ) {
            throw BusinessException(StatusCode.RUN_AGAIN_AFTER_AWHILE)
        }

        return try {
            joinPoint.proceed()
        } finally {
            lockService.unlock(distributedLock.domain, lockKey)
        }
    }
}
