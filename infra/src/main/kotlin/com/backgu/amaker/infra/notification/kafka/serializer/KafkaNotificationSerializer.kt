package com.backgu.amaker.infra.notification.kafka.serializer

import com.backgu.amaker.domain.notifiacation.Notification
import org.apache.kafka.common.serialization.Serializer
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

class KafkaNotificationSerializer : Serializer<Notification> {
    override fun serialize(
        topic: String?,
        data: Notification,
    ): ByteArray? =
        try {
            ByteArrayOutputStream().use { byteStream ->
                ObjectOutputStream(byteStream).use { objectStream ->
                    objectStream.writeObject(data)
                }
                byteStream.toByteArray()
            }
        } catch (e: Exception) {
            throw IllegalArgumentException("Error serializing Notification", e)
        }
}
