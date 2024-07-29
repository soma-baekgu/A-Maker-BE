package com.backgu.amaker.api.event.dto.query

import com.backgu.amaker.api.event.dto.ReplyQuery
import io.swagger.v3.oas.annotations.media.Schema

data class ReplyQueryRequest(
    @Schema(description = "읽어올 페이지 번호", example = "2", defaultValue = "0")
    val page: Int = 0,
    @Schema(description = "읽어올 응답의 개수", example = "100", defaultValue = "20")
    val size: Int = 20,
) {
    fun toDto(eventId: Long): ReplyQuery =
        ReplyQuery(
            eventId = eventId,
            size = size,
        )
}
