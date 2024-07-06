package com.backgu.amaker.common.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Validation
import jakarta.validation.constraints.Email

class EmailListValidator : ConstraintValidator<ValidEmailList, Set<String>> {
    private val validator = Validation.buildDefaultValidatorFactory().validator

    override fun isValid(
        emails: Set<String>?,
        context: ConstraintValidatorContext,
    ): Boolean {
        if (emails == null) return true

        return emails.all { email ->
            validator.validate(EmailHolder(email)).isEmpty()
        }
    }

    private class EmailHolder(
        @field:Email
        val email: String,
    )
}
