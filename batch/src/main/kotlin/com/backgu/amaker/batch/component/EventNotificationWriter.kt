package com.backgu.amaker.batch.component

import com.backgu.amaker.batch.dto.NotificationWithEntityDto
import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class EventNotificationWriter(
    @Qualifier("entityManagerFactory")
    private val entityManagerFactory: EntityManagerFactory,
) : ItemWriter<NotificationWithEntityDto> {
    override fun write(chunk: Chunk<out NotificationWithEntityDto>) {
        val entities =
            chunk.items.map { item ->
                item.eventNotificationEntity
            }

        jpaItemWriter.write(Chunk(entities))
    }

    private val jpaItemWriter: JpaItemWriter<EventNotificationEntity> =
        JpaItemWriterBuilder<EventNotificationEntity>()
            .entityManagerFactory(entityManagerFactory)
            .build()
}
