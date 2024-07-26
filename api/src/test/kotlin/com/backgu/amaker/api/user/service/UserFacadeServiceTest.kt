package com.backgu.amaker.api.user.service

import com.backgu.amaker.api.fixture.UserFixture
import com.backgu.amaker.api.user.dto.EmailExistsDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("UserFacadeService 테스트")
@Transactional
@SpringBootTest
class UserFacadeServiceTest {
    @Autowired
    private lateinit var userFacadeService: UserFacadeService

    @Autowired
    private lateinit var userFixture: UserFixture

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
}
