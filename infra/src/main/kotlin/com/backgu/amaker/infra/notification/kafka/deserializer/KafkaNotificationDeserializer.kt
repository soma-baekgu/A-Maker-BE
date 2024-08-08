package com.backgu.amaker.infra.notification.kafka.deserializer

import com.backgu.amaker.domain.notifiacation.Notification
import org.apache.kafka.common.serialization.Deserializer
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

class KafkaNotificationDeserializer : Deserializer<Notification> {
    override fun deserialize(
        topic: String,
        data: ByteArray,
    ): Notification? =
        try {
            ByteArrayInputStream(data).use { byteStream ->
                ObjectInputStream(byteStream).use { objectStream ->
                    objectStream.readObject() as Notification
                }
            }
        } catch (e: Exception) {
            null
        }
}
