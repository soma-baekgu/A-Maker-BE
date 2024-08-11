package com.backgu.amaker.batch.config

import com.backgu.amaker.batch.component.EventNotificationProcessor
import com.backgu.amaker.batch.component.EventNotificationReader
import com.backgu.amaker.batch.component.EventNotificationWriter
import com.backgu.amaker.batch.component.NotificationPublisherWriter
import com.backgu.amaker.batch.dto.NotificationWithEntityDto
import com.backgu.amaker.batch.query.EventNotificationQuery
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.support.CompositeItemWriter
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class EventNotificationConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val eventNotificationReader: EventNotificationReader,
    private val eventNotificationProcessor: EventNotificationProcessor,
    private val eventNotificationWriter: EventNotificationWriter,
    private val notificationPublisherWriter: NotificationPublisherWriter,
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
            .chunk<EventNotificationQuery, NotificationWithEntityDto>(1000, transactionManager)
            .reader(eventNotificationReader.reader())
            .processor(eventNotificationProcessor.processor())
            .writer(compositeWriter())
            .build()

    @Bean
    @StepScope
    fun compositeWriter(): CompositeItemWriter<NotificationWithEntityDto> =
        CompositeItemWriterBuilder<NotificationWithEntityDto>()
            .delegates(listOf(eventNotificationWriter, notificationPublisherWriter))
            .build()
}
