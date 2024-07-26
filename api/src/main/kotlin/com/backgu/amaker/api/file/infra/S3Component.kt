package com.backgu.amaker.api.file.infra

import com.backgu.amaker.api.config.AWSConfig
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Service
class S3Component(
    val s3Presigner: S3Presigner,
    val awsConfig: AWSConfig,
) {
    fun generatePresignedUrl(keyName: String): String {
        val bucketName = awsConfig.s3.bucket

        val objectRequest =
            PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key(keyName)
                .build()

        val presignRequest =
            PutObjectPresignRequest
                .builder()
                .signatureDuration(Duration.ofMinutes(3))
                .putObjectRequest(objectRequest)
                .build()

        val presignedRequest = s3Presigner.presignPutObject(presignRequest)
        return presignedRequest.url().toExternalForm()
    }
}
