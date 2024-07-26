package com.backgu.amaker.api.file.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

data class FileResponse(
    @Schema(
        description = "파일 경로",
        example =
            "https://a-maker.com" +
                "/abc/2024-07-26T03:36:01.202Z" +
                "/hi.png",
    )
    val path: String,
    @Schema(description = "파일 이름", example = "hi.png")
    val fileName: String,
) {
    companion object {
        fun of(filePath: String): FileResponse =
            FileResponse(
                path = filePath,
                fileName = URLDecoder.decode(filePath.substringAfterLast('/'), StandardCharsets.UTF_8.toString()),
            )
    }
}
