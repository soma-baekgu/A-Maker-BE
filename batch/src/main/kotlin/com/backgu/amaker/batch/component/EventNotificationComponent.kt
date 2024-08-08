package com.backgu.amaker.batch.component

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.batch.dto.EventNotificationCreateDto
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.event.EventNotCompleted
import com.backgu.amaker.domain.notifiacation.event.EventOverdue
import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.RowMapper
import org.springframework.transaction.PlatformTransactionManager
import java.sql.ResultSet
import java.time.Duration
import java.time.LocalDateTime
import javax.sql.DataSource

@Configuration
class EventNotificationComponent(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    @Qualifier("domainDataSource")
    private val domainDataSource: DataSource,
    private val notificationEventService: NotificationEventService,
    @Qualifier("entityManagerFactory")
    private val entityManagerFactory: EntityManagerFactory,
) {
    @Bean
    fun eventNotificationJob(): Job =
        JobBuilder("eventNotificationJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(step())
            .build()

    @Bean
    fun step(): Step =
        StepBuilder("eventNotificationStep", jobRepository)
            .chunk<EventNotificationCreateDto, EventNotificationEntity>(1000, transactionManager)
            .reader(reader(domainDataSource))
            .processor(processor())
            .writer(writer())
            .build()

    @Bean
    fun reader(dataSource: DataSource): JdbcCursorItemReader<EventNotificationCreateDto> =
        JdbcCursorItemReaderBuilder<EventNotificationCreateDto>()
            .name("EventNotificationCreateDtoReader")
            .dataSource(dataSource)
            .sql(
                """
                select eau.id as event_assigned_user_id,
                       eau.user_id,
                       eau.event_id,
                       e.event_title,
                       e.notification_start_time,
                       e.notification_interval,
                       e.dead_line,
                       n.id         as notification_id,
                       n.created_at as notification_created_at,
                       u.email
                from event_assigned_user eau
                         join
                     event e on eau.event_id = e.id
                         left join
                     (select n1.id,
                             n1.user_id,
                             en.event_id,
                             n1.created_at
                      from notification n1
                               join
                           event_notification en on n1.id = en.id
                               join
                           (select max(n2.id) as max_id,
                                   en2.event_id,
                                   n2.user_id
                            from notification n2
                                     join
                                 event_notification en2 on n2.id = en2.id
                            group by en2.event_id,
                                     n2.user_id) latest
                           on n1.id = latest.max_id) n on n.user_id = eau.user_id
                         and n.event_id = e.id
                         join users u on eau.user_id = u.id
                where eau.is_finished = false;
                """.trimIndent(),
            ).rowMapper(eventNotificationCreateDtoRowMapper())
            .build()

    @Bean
    fun eventNotificationCreateDtoRowMapper(): RowMapper<EventNotificationCreateDto> =
        RowMapper { rs: ResultSet, _: Int ->
            EventNotificationCreateDto(
                eventAssignedUserId = rs.getLong("event_assigned_user_id"),
                userId = rs.getString("user_id"),
                eventId = rs.getLong("event_id"),
                eventTitle = rs.getString("event_title"),
                notificationStartTime = rs.getTimestamp("notification_start_time").toLocalDateTime(),
                notificationInterval = rs.getInt("notification_interval"),
                deadline = rs.getTimestamp("dead_line").toLocalDateTime(),
                notificationId = rs.getLong("notification_id").takeIf { !rs.wasNull() },
                notificationCreatedAt = rs.getTimestamp("notification_created_at")?.toLocalDateTime(),
                email = rs.getString("email"),
            )
        }

    @Bean
    fun processor(): ItemProcessor<EventNotificationCreateDto, EventNotificationEntity> =
        ItemProcessor { item ->
            val now = LocalDateTime.now()

            if (item.notificationStartTime.isBefore(now)) {
                val shouldSendNotification =
                    item.notificationId == null ||
                        item.notificationCreatedAt
                            ?.plusMinutes(item.notificationInterval.toLong())
                            ?.isBefore(now) ?: false

                if (shouldSendNotification) {
                    val notification: Notification =
                        if (item.deadline.isBefore(now)) {
                            val eventOverdue = EventOverdue.of(item.userId, Duration.between(item.deadline, now))
                            notificationEventService.publishNotificationEvent(eventOverdue)
                            eventOverdue
                        } else {
                            val eventNotCompleted = EventNotCompleted.of(item.userId, Duration.between(now, item.deadline))
                            notificationEventService.publishNotificationEvent(eventNotCompleted)
                            eventNotCompleted
                        }

                    EventNotificationEntity(
                        title = notification.method.title,
                        content = notification.method.message,
                        userId = item.userId,
                        eventId = item.eventId,
                    )
                } else {
                    null
                }
            } else {
                null
            }
        }

    @Bean
    fun writer(): JpaItemWriter<EventNotificationEntity> =
        JpaItemWriterBuilder<EventNotificationEntity>()
            .entityManagerFactory(entityManagerFactory)
            .build()
}
