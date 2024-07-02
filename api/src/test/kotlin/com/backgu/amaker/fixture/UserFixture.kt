package com.backgu.amaker.fixture

import com.backgu.amaker.common.infra.StubIdPublisher
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.jpa.UserEntity
import com.backgu.amaker.user.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFixture(
    private val userRepository: UserRepository,
    private val stubIdPublisher: StubIdPublisher,
) {
    companion object {
        fun createUser(
            id: String = UUID.randomUUID().toString(),
            name: String = nameBuilder(id),
            email: String = emailBuilder(id),
            picture: String = pictureBuilder(id),
        ): User =
            User(
                id = id,
                name = name,
                email = email,
                picture = picture,
            )

        private fun nameBuilder(id: Any): String = "name-$id"

        private fun emailBuilder(id: Any): String = "$id@gmail.com"

        private fun pictureBuilder(id: Any): String = "http://server/picture-$id"
    }

    fun createPersistedUser(
        id: Any = stubIdPublisher.publishId(),
        name: String = nameBuilder(id),
        email: String = emailBuilder(id),
        picture: String = pictureBuilder(id),
    ) = userRepository
        .save(
            UserEntity.of(createUser(id.toString(), name, email, picture)),
        ).toDomain()

    fun createPersistedUsers(count: Long): List<User> = (1..count).map { createPersistedUser(id = it) }

    fun createPersistedUsers(vararg ids: Any): List<User> = ids.map { createPersistedUser(it.toString()) }

    fun deleteAll() {
        userRepository.deleteAll()
    }
}
