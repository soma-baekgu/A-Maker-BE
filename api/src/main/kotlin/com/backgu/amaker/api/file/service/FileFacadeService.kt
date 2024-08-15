package com.backgu.amaker.api.file.service

import com.backgu.amaker.common.id.IdPublisher
import org.springframework.stereotype.Service

@Service
class FileFacadeService(
    val fileService: FileService,
    val idPublisher: IdPublisher,
) {
    fun getSavePath(
        path: String,
        userId: String,
        name: String?,
        extension: String,
    ): String {
        val fileName = name ?: getRandomFileName()
        val filePath = "$userId/$path/$fileName.$extension"
        return fileService.generatePresignedUrl(filePath)
    }

    private fun getRandomFileName(): String = idPublisher.publishId()
}
