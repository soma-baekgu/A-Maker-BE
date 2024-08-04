package com.backgu.amaker.api.realtime.dto.response

data class RealTimeServerHealthResponse(
    val status: String,
) {
    fun isValid(): Boolean = status == "UP"
}
