package com.backgu.amaker.api.user.service

import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.fixture.UserDeviceFixture
import com.backgu.amaker.api.fixture.UserFixture
import com.backgu.amaker.api.user.dto.EmailExistsDto
import com.backgu.amaker.api.user.dto.UserDeviceDto
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.user.Device
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("UserFacadeService 테스트")
@Transactional
class UserFacadeServiceTest : IntegrationTest() {
    @Autowired
    private lateinit var userFacadeService: UserFacadeService

    @Autowired
    private lateinit var userFixture: UserFixture

    @Autowired
    private lateinit var userDeviceFixture: UserDeviceFixture

    @Test
    @DisplayName("등록된 이메일인지 확인 true")
    fun existsByEmailTrue() {
        // given
        val email = "test@gmail.com"
        userFixture.createPersistedUser(email = email)

        // when
        val emailExistsDto: EmailExistsDto = userFacadeService.existsByEmail(email)

        // then
        assertThat(emailExistsDto).isNotNull
        assertThat(emailExistsDto.exists).isTrue()
    }

    @Test
    @DisplayName("등록된 이메일인지 확인 false")
    fun existsByEmailFalse() {
        // given
        val email = "test@gmail.com"
        val diffEmail = "diff-test@gmail.com"
        userFixture.createPersistedUser(email = email)

        // when
        val emailExistsDto: EmailExistsDto = userFacadeService.existsByEmail(diffEmail)

        // then
        assertThat(emailExistsDto).isNotNull
        assertThat(emailExistsDto.exists).isFalse()
    }

    @Test
    @DisplayName("사용자 디바이스 등록")
    fun registerUserDevice() {
        // given
        val user = userFixture.createPersistedUser()
        val userDeviceDto =
            UserDeviceDto(
                userId = user.id,
                token = "fcmToken",
                device = Device.ANDROID,
            )

        // when
        val savedUserDeviceDto = userFacadeService.registerUserDevice(userDeviceDto)

        // then
        assertThat(savedUserDeviceDto).isNotNull
        assertThat(savedUserDeviceDto.userId).isEqualTo(user.id)
        assertThat(savedUserDeviceDto.token).isEqualTo(userDeviceDto.token)
        assertThat(savedUserDeviceDto.device).isEqualTo(userDeviceDto.device)
    }

    @Test
    @DisplayName("사용자 디바이스 등록 실패")
    fun registerUserDeviceFail() {
        // given
        val user = userFixture.createPersistedUser()
        userDeviceFixture.createPersistedUserDevice(user.id, "fcmToken", Device.ANDROID)
        val userDeviceDto =
            UserDeviceDto(
                userId = user.id,
                token = "fcmToken",
                device = Device.ANDROID,
            )

        // when & then
        assertThatThrownBy { userFacadeService.registerUserDevice(userDeviceDto) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.USER_DEVICE_DUPLICATED)
    }
}
