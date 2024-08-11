package com.backgu.amaker.batch.config

import com.backgu.amaker.batch.common.container.IntegrationTest
import com.backgu.amaker.batch.fixture.BatchFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

@DisplayName("EventNotificationConfigTest 테스트")
class EventNotificationConfigTest : IntegrationTest() {
    @Autowired
    lateinit var batchFixture: BatchFixture

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @BeforeEach
    fun setup() {
        batchFixture.setup()
    }

    @AfterEach
    fun tearDown() {
        batchFixture.deleteAll()
    }

    @Test
    @DisplayName("이벤트 알림 배치 테스트")
    fun eventNotificationConfigTest() {
        // given

        // when
        val jobExecution = jobLauncherTestUtils.launchJob()

        // then
        assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)
        val stepExecutions = jobExecution.stepExecutions
        var totalProcessedItems = 0L

        for (stepExecution in stepExecutions) {
            totalProcessedItems += stepExecution.writeCount
        }

        assertThat(totalProcessedItems).isEqualTo(24)
    }
}
