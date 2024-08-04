package com.backgu.amaker.common.http.response

import io.swagger.v3.oas.annotations.media.Schema

interface PageResponse<T> {
    @get:Schema(description = "요소 리스트")
    val content: List<T>

    @get:Schema(description = "페이지 번호", example = "0")
    val pageNumber: Int

    @get:Schema(description = "페이지 사이즈", example = "10", defaultValue = "20")
    val pageSize: Int

    @get:Schema(description = "총 요소 수", example = "25")
    val totalElements: Long

    @get:Schema(description = "총 페이지 수", example = "3")
    val totalPages: Int

    @get:Schema(description = "다음 페이지 존재 유무", example = "true")
    val hasNext: Boolean

    @get:Schema(description = "이전 페이지 존재 유무", example = "false")
    val hasPrevious: Boolean

    @get:Schema(description = "첫 페이지인지", example = "true")
    val isFirst: Boolean

    @get:Schema(description = "마지막 페이지인지", example = "false")
    val isLast: Boolean
}
