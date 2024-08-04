package com.backgu.amaker.api.common.exception

import com.backgu.amaker.api.common.dto.response.ApiError
import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.common.status.StatusCode
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
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
        request: HttpServletRequest,
    ): ResponseEntity<ApiError<Map<String, String>>> {
        val errorMap: Map<String, String> =
            e.bindingResult.fieldErrors.associate { error ->
                error.field to (error.defaultMessage ?: "Invalid value")
            }

        return ResponseEntity
            .badRequest()
            .body(apiHandler.onFailure(StatusCode.INVALID_INPUT_VALUE, errorMap))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError<Unit>> =
        ResponseEntity
            .badRequest()
            .body(apiHandler.onFailure(StatusCode.INVALID_INPUT_VALUE))

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(
        e: HandlerMethodValidationException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError<Map<String, String>>> {
        val errorMap: Map<String, String> =
            e.valueResults.associate { error ->
                error.argument.toString() to (
                    error.resolvableErrors
                        .filterIsInstance<DefaultMessageSourceResolvable>()
                        .joinToString(", ") { it.defaultMessage ?: "Invalid value" }
                )
            }

        return ResponseEntity
            .badRequest()
            .body(apiHandler.onFailure(StatusCode.INVALID_INPUT_VALUE, errorMap))
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        e: BusinessException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError<Unit>> = ResponseEntity.status(e.httpStatus).body(apiHandler.onFailure(e.statusCode))

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
        e: RuntimeException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError<Unit>> {
        logger.info(e) { "${request.method}: ${request.requestURI}" }
        return ResponseEntity
            .internalServerError()
            .body(apiHandler.onFailure(StatusCode.INTERNAL_SERVER_ERROR))
    }
}
