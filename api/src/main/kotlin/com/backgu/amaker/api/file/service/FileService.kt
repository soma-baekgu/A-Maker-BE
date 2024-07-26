package com.backgu.amaker.api.file.service

import com.backgu.amaker.api.file.infra.S3Component
import org.springframework.stereotype.Component

@Component
class FileService(
    val s3Component: S3Component,
) {
    fun generatePresignedUrl(objectKey: String): String = s3Component.generatePresignedUrl(objectKey)
}
