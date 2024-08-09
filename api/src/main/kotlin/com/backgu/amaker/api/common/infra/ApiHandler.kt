package com.backgu.amaker.api.common.infra

import com.backgu.amaker.api.common.service.ClockHolder
import com.backgu.amaker.common.http.response.ApiError
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.http.response.ApiSuccess
import com.backgu.amaker.common.status.StatusCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class ApiHandler(
    private val request: HttpServletRequest,
    private val response: HttpServletResponse,
    private val clockHolder: ClockHolder,
) {
    fun <T> onSuccess(
        data: T,
        code: StatusCode = StatusCode.SUCCESS,
    ): ApiResult<T> =
        ApiSuccess(
            timestamp = clockHolder.now(),
            path = request.requestURI,
            status = code.code,
            data = data,
        )

    fun <T> onFailure(
        statusCode: StatusCode,
        data: T? = null,
    ): ApiError<T> =
        ApiError.of(
            timestamp = clockHolder.now(),
            status = statusCode.code,
            path = request.requestURI,
            message = statusCode.message,
            data = data,
        )
}