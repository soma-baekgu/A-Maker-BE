package com.backgu.amaker.realtime.common.container

import com.redis.testcontainers.RedisContainer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest {
    companion object {
        val SERVER_ID = 12345L
        private val REDIS_CONTAINER: RedisContainer = RedisContainer(RedisContainer.DEFAULT_IMAGE_NAME.withTag("6"))
        private val MYSQL_CONTAINER: MySQLContainer<*> =
            MySQLContainer(DockerImageName.parse("mysql:8.0.26"))
                .withDatabaseName("amaker")
                .withUsername("user")
                .withPassword("password")

        init {
            REDIS_CONTAINER.start()
            MYSQL_CONTAINER.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { REDIS_CONTAINER.host }
            registry.add("spring.data.redis.port") { REDIS_CONTAINER.firstMappedPort }
            registry.add("spring.datasource.url") { MYSQL_CONTAINER.jdbcUrl }
            registry.add("spring.datasource.username") { MYSQL_CONTAINER.username }
            registry.add("spring.datasource.password") { MYSQL_CONTAINER.password }
        }
    }
}
