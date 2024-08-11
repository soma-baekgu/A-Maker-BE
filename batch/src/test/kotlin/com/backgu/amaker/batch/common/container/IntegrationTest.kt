package com.backgu.amaker.batch.common.container

import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBatchTest
@SpringBootTest
@Testcontainers
class IntegrationTest {
    companion object {
        private val MYSQL_CONTAINER: MySQLContainer<*> =
            MySQLContainer(DockerImageName.parse("mysql:8.0.26"))
                .withDatabaseName("amaker")
                .withUsername("user")
                .withPassword("password")
        private val KAFKA_CONTAINER: KafkaContainer =
            KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"))

        init {
            MYSQL_CONTAINER.start()
            KAFKA_CONTAINER.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { MYSQL_CONTAINER.jdbcUrl }
            registry.add("spring.datasource.username") { MYSQL_CONTAINER.username }
            registry.add("spring.datasource.password") { MYSQL_CONTAINER.password }
            registry.add("spring.kafka.bootstrap-servers") { KAFKA_CONTAINER.bootstrapServers }
        }
    }
}
