package com.backgu.amaker.common.dto.response

import io.swagger.v3.oas.annotations.media.Schema

class ApiError<T>(
    timestamp: String,
    path: String,
    status: Int,
    data: T,
    @Schema(description = "에러 reason Phrase", example = "Bad Request")
    val error: String,
) : ApiResult<T>(
        timestamp = timestamp,
        path = path,
        status = status,
        data = data,
    )
