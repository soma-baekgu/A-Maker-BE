package com.backgu.amaker.infra.notification.kafka.deserializer

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer

class KafkaNotificationDeserializer : Deserializer<NotificationEvent> {
    override fun deserialize(
        topic: String?,
        data: ByteArray?,
    ): EmailEvent {
        val data: String = data?.toString(Charsets.UTF_8).toString()
        val objectMapper: ObjectMapper = ObjectMapper()
        return objectMapper.readValue(data, EmailEvent::class.java)
    }
}
