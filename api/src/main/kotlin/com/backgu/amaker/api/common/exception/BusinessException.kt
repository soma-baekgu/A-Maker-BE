package com.backgu.amaker.api.common.exception

import com.backgu.amaker.common.status.StatusCode
import org.springframework.http.HttpStatus
import kotlin.RuntimeException

class BusinessException(
    val statusCode: StatusCode,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = statusCode.message,
) : RuntimeException(message) {
    constructor(statusCode: StatusCode, message: String) : this(statusCode, HttpStatus.BAD_REQUEST, message)
}
