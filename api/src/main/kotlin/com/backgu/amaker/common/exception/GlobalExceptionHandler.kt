package com.backgu.amaker.common.exception

import com.backgu.amaker.common.dto.response.ApiError
import com.backgu.amaker.common.infra.ApiHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(
    private val apiHandler: ApiHandler,
) {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiError<Map<String, String>>> {
        val errorMap: Map<String, String> =
            e.bindingResult.fieldErrors.associate { error ->
                error.field to (error.defaultMessage ?: "Invalid value")
            }

        return ResponseEntity.badRequest().body(apiHandler.onFailure(errorMap))
    }
}