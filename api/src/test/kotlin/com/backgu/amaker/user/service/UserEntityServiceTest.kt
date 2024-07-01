package com.backgu.amaker.user.service

import com.backgu.amaker.fixture.UserFixture
import com.backgu.amaker.user.domain.UserRole
import com.backgu.amaker.user.dto.UserCreateDto
import com.backgu.amaker.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@DisplayName("UserService 테스트")
@Transactional
@SpringBootTest
class UserEntityServiceTest {
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

    @Test
    @DisplayName("이메일로 사용자 조회 테스트")
    fun getByEmail() {
        // given
        val userCreateDto = UserCreateDto("test", "test@gmail.com", "test")
        val saveUser = userService.saveUser(userCreateDto)

        // when
        val result = userService.getByEmail(saveUser.email)

        // then
        assertThat(result.id).isNotNull()
        assertThat(result.name).isEqualTo(saveUser.name)
        assertThat(result.email).isEqualTo(saveUser.email)
        assertThat(result.picture).isEqualTo(saveUser.picture)
        assertThat(result.userRole).isEqualTo(UserRole.USER)
    }

    @Test
    @DisplayName("존재하지 않은 이메일로 사용자 조회 테스트")
    fun getNoEmailUserTest() {
        // given & when & then
        assertThatThrownBy { userService.getByEmail("no@gmail.com") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("User not found")
    }

    @Test
    @DisplayName("saveOrGetUser: 존재하는 사용자 조회 테스트")
    fun saveOrGetUserTest() {
        // given
        val userCreateDto = UserCreateDto("pre", "pre@gmail.com", "pre")
        val saveUser = userService.saveUser(userCreateDto)

        // when
        val result = userService.saveOrGetUser(userCreateDto)

        // then
        assertThat(result.id).isEqualTo(saveUser.id)
        assertThat(result.name).isEqualTo(saveUser.name)
        assertThat(result.email).isEqualTo(saveUser.email)
        assertThat(result.picture).isEqualTo(saveUser.picture)
        assertThat(result.userRole).isEqualTo(UserRole.USER)
    }

    @Test
    @DisplayName("saveOrGetUser: 존재하지 않는 사용자 저장 테스트")
    fun saveOrGetUser_SaveTest() {
        // given
        val userCreateDto = UserCreateDto("new", "new@gmail.com", "new")
        val savedUser = userService.saveOrGetUser(userCreateDto)

        // when
        val result = userService.getByEmail(savedUser.email)

        // then
        assertThat(result.id).isNotNull()
        assertThat(result.name).isEqualTo(userCreateDto.name)
        assertThat(result.email).isEqualTo(userCreateDto.email)
        assertThat(result.picture).isEqualTo(userCreateDto.picture)
        assertThat(result.userRole).isEqualTo(UserRole.USER)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired userRepository: UserRepository,
        ) {
            UserFixture(userRepository).testUserSetUp()
        }
    }
}
