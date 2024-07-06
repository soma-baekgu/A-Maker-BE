package com.backgu.amaker.common.exception

import com.backgu.amaker.common.dto.response.ApiError
import com.backgu.amaker.common.infra.ApiHandler
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(
    private val apiHandler: ApiHandler,
) {
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ApiError<Unit>> {
        e.printStackTrace()
        return ResponseEntity
            .internalServerError()
            .body(apiHandler.onFailure(StatusCode.INTERNAL_SERVER_ERROR))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiError<Map<String, String>>> {
        val errorMap: Map<String, String>? =
            e.bindingResult?.fieldErrors?.associate { error ->
                error.field to (error.defaultMessage ?: "Invalid value")
            }

        return ResponseEntity
            .badRequest()
            .body(apiHandler.onFailure(StatusCode.INVALID_INPUT_VALUE, errorMap))
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiError<Unit>> =
        ResponseEntity.status(e.httpStatus).body(apiHandler.onFailure(e.statusCode))
}
