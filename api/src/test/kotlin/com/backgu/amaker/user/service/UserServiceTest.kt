package com.backgu.amaker.user.service

import com.backgu.amaker.fixture.UserFixture
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.domain.UserRole
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
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @Test
    @DisplayName("사용자 저장 테스트")
    fun saveUser() {
        // given
        val request = UserFixture.createUser()

        // when
        val result = userService.save(request)

        // then
        assertThat(result).isNotNull()
    }

    @Test
    @DisplayName("이메일로 사용자 조회 테스트")
    fun getByEmail() {
        // given
        val user = User(name = "test", email = "test@gmail.com", picture = "test")
        val saveUser = userService.save(user)

        // when
        val result = userService.getByEmail(saveUser.email)

        // then
        assertThat(result).isNotNull()
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
        val user = User(name = "pre", email = "pre@gmail.com", picture = "pre")
        val saveUser = userService.save(user)

        // when
        val result = userService.saveOrGetUser(user)

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
        val user = User(name = "new", email = "new@gmail.com", picture = "new")
        val savedUser = userService.saveOrGetUser(user)

        // when
        val result = userService.getByEmail(savedUser.email)

        // then
        assertThat(result.id).isNotNull()
        assertThat(result.name).isEqualTo(user.name)
        assertThat(result.email).isEqualTo(user.email)
        assertThat(result.picture).isEqualTo(user.picture)
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
