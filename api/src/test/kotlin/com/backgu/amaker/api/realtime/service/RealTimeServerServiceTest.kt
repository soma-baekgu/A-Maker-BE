package com.backgu.amaker.api.realtime.service

import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.infra.redis.realtime.repository.RealTimeServerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("RealTimeServerService 테스트")
class RealTimeServerServiceTest : IntegrationTest() {
    @Autowired
    lateinit var realTimeServerService: RealTimeServerService

    @Autowired
    lateinit var realTimeServerRepository: RealTimeServerRepository

    @AfterEach
    fun tearDown() {
        realTimeServerRepository.deleteAll()
    }

    @Test
    @DisplayName("save 테스트")
    fun saveTest() {
        // given
        val realTimeServer =
            RealTimeServer(
                id = 1,
                address = "127.0.0.1",
                port = 8080,
                schema = "http",
            )

        // when
        val save: RealTimeServer = realTimeServerService.save(realTimeServer)
        val findRealTimeServer = realTimeServerRepository.findById(save.id).orElseThrow()

        // then
        assertThat(findRealTimeServer.id).isNotNull()
        assertThat(findRealTimeServer.address).isEqualTo(realTimeServer.address)
        assertThat(findRealTimeServer.port).isEqualTo(realTimeServer.port)
        assertThat(findRealTimeServer.schema).isEqualTo(realTimeServer.schema)
    }

    @Test
    @DisplayName("delete 테스트")
    fun deleteTest() {
        // given
        val realTimeServer =
            RealTimeServer(
                id = 1,
                address = "127.0.0.1",
                port = 8080,
                schema = "http",
            )

        val save: RealTimeServer = realTimeServerService.save(realTimeServer)

        // when
        realTimeServerService.delete(save.id)

        // then
        assertThat(realTimeServerRepository.findById(save.id)).isEmpty()
    }

    @Test
    @DisplayName("findAll 테스트")
    fun findAllTest() {
        // given
        val realTimeServer1 =
            RealTimeServer(
                id = 1,
                address = "168.0.0.1",
                port = 8080,
                schema = "http",
            )
        val realTimeServer2 =
            RealTimeServer(
                id = 2,
                address = "168.0.0.2",
                port = 8080,
                schema = "http",
            )
        val realTimeServer3 =
            RealTimeServer(
                id = 3,
                address = "168.0.0.3",
                port = 8080,
                schema = "http",
            )

        realTimeServerService.save(realTimeServer1)
        realTimeServerService.save(realTimeServer2)
        realTimeServerService.save(realTimeServer3)

        // when
        val findAll: List<RealTimeServer> = realTimeServerService.findAll()
        println(findAll)

        // then
        assertThat(findAll.size).isEqualTo(3)
        assertThat(findAll[0].address).isEqualTo(realTimeServer1.address)
        assertThat(findAll[1].address).isEqualTo(realTimeServer2.address)
        assertThat(findAll[2].address).isEqualTo(realTimeServer3.address)
    }
}
