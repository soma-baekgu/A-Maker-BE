package com.backgu.amaker.common.http.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

class ApiError<T>(
    override val timestamp: String,
    override val status: String,
    override val path: String,
    override val data: ErrorResponse<T>,
) : ApiResult<ApiError.ErrorResponse<T>> {
    data class ErrorResponse<U>(
        @Schema(description = "에러 미시지", example = "워크스페이스를 찾을 수 없습니다.")
        val message: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val detail: U?,
    )

    companion object {
        fun <U> of(
            timestamp: String,
            status: String,
            path: String,
            message: String,
            data: U?,
        ): ApiError<U> =
            ApiError(
                timestamp = timestamp,
                status = status,
                path = path,
                data = ErrorResponse(message, data),
            )
    }
}
