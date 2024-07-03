package com.backgu.amaker.common.infra

import com.backgu.amaker.common.dto.response.ApiError
import com.backgu.amaker.common.dto.response.ApiResult
import com.backgu.amaker.common.service.ClockHolder
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ApiHandler(
    private val request: HttpServletRequest,
    private val response: HttpServletResponse,
    private val clockHolder: ClockHolder,
) {
    fun <T> onSuccess(
        data: T,
        status: HttpStatus = HttpStatus.OK,
    ): ApiResult<T> =
        ApiResult(
            timestamp = clockHolder.now(),
            path = request.requestURI,
            status = status.value(),
            data = data,
        )

    fun <T> onFailure(
        error: T,
        status: HttpStatus = HttpStatus.BAD_REQUEST,
    ): ApiError<T> =
        ApiError<T>(
            timestamp = clockHolder.now(),
            path = request.requestURI,
            status = status.value(),
            data = error,
            error = status.reasonPhrase,
        )
}
