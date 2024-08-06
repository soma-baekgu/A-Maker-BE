package com.backgu.amaker.api.realtime.service

import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.realtime.dto.RealTimeServerDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.InetAddress
import kotlin.test.Test

@DisplayName("RealTimeFacadeService 테스트")
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class RealTimeFacadeServiceTest : IntegrationTest() {
    @Autowired
    lateinit var realTimeFacadeService: RealTimeFacadeService

    @MockkBean
    lateinit var realTimeServerHealthCheckService: RealTimeServerHealthCheckService

    @Test
    @DisplayName("connect 테스트")
    fun connectTest() {
        // given
        val clientAddress = InetAddress.getByName("127.0.0.1")
        val serverPort = 8080

        // when
        val connect: RealTimeServerDto = realTimeFacadeService.connect(clientAddress, serverPort)

        // then
        assertThat(connect.id).isNotNull()
        assertThat(connect.address).isEqualTo(clientAddress.hostAddress)
        assertThat(connect.port).isEqualTo(serverPort)
    }

    @Test
    @DisplayName("checkServerStatus 테스트")
    fun checkServerStatusTest() {
        // when
        every { realTimeServerHealthCheckService.healthCheck(any()) } returns true

        // then
        realTimeFacadeService.checkServerStatus()

        // then
        assertThat(true).isTrue()
    }
}
