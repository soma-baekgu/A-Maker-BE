package com.backgu.amaker.api.common.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailListValidator::class])
annotation class ValidEmailList(
    val message: String = "Invalid email address in the list",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
