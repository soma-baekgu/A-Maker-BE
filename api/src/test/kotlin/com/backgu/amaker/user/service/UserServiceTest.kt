package com.backgu.amaker.user.service

import com.backgu.amaker.fixture.UserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@DisplayName("UserService 테스트")
@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @Test
    @DisplayName("사용자 저장 테스트")
    fun saveUser() {
        // given
        val request = UserFixture.createUserRequest()

        // when
        val result = userService.saveUser(request)

        // then
        assertThat(result).isNotNull()
    }
}
