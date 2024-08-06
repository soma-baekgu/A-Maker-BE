package com.backgu.amaker.batch.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulerConfig(
    private val jobLauncher: JobLauncher,
    private val eventNotificationJob: Job,
) {
    @Scheduled(cron = "0 * * * * ?")
    @Throws(Exception::class)
    fun runJob() {
        val jobParameters =
            JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()
        jobLauncher.run(eventNotificationJob, jobParameters)
    }
}
