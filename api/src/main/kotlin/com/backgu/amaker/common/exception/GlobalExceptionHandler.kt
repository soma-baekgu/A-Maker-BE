package com.backgu.amaker.common.exception

import com.backgu.amaker.common.dto.response.ApiError
import com.backgu.amaker.common.infra.ApiHandler
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler(
    private val apiHandler: ApiHandler,
) {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        httpServletRequest: HttpServletRequest,
    ): ResponseEntity<ApiError<Map<String, String>>> {
        val errorMap: Map<String, String>? =
            e.bindingResult?.fieldErrors?.associate { error ->
                error.field to (error.defaultMessage ?: "Invalid value")
            }

        logger.error { e.printStackTrace() }
        errorMap?.forEach { (field, message) ->
            logger.error { "Validation error on field: $field - Message: $message" }
        }

        return ResponseEntity
            .badRequest()
            .body(apiHandler.onFailure(StatusCode.INVALID_INPUT_VALUE, errorMap))
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(e: HandlerMethodValidationException): ResponseEntity<ApiError<Map<String, String>>> {
        val errorMap: Map<String, String> =
            e.valueResults.associate { error ->
                error.argument.toString() to (
                    error.resolvableErrors
                        .filterIsInstance<DefaultMessageSourceResolvable>()
                        .joinToString(", ") { it.defaultMessage ?: "Invalid value" }
                )
            }

        logger.error { e.printStackTrace() }
        errorMap.forEach { (field, message) ->
            logger.error { "Validation error on field: $field - Message: $message" }
        }

        return ResponseEntity
            .badRequest()
            .body(apiHandler.onFailure(StatusCode.INVALID_INPUT_VALUE, errorMap))
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiError<Unit>> =
        ResponseEntity.status(e.httpStatus).body(apiHandler.onFailure(e.statusCode))

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ApiError<Unit>> {
        e.printStackTrace()
        return ResponseEntity
            .internalServerError()
            .body(apiHandler.onFailure(StatusCode.INTERNAL_SERVER_ERROR))
    }
}
