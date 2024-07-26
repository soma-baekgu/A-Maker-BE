package com.backgu.amaker.api.common.dto.response

class ApiSuccess<T>(
    override val timestamp: String,
    override val status: String,
    override val path: String,
    override val data: T,
) : ApiResult<T>
