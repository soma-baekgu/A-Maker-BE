package com.backgu.amaker.file.service

import com.backgu.amaker.file.infra.S3Component
import org.springframework.stereotype.Service

@Service
class FileService(
    val s3Component: S3Component,
) {
    fun generatePresignedUrl(objectKey: String): String = s3Component.generatePresignedUrl(objectKey)
}
