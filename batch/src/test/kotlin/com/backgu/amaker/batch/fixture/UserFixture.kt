package com.backgu.amaker.batch.fixture

import com.backgu.amaker.batch.common.infra.StubIdPublisher
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.user.entity.UserEntity
import com.backgu.amaker.infra.jpa.user.reposotory.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFixture(
    val userRepository: UserRepository,
    val stubIdPublisher: StubIdPublisher,
) {
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

    fun createPersistedUsers(count: Long): List<User> = (1..count).map { createPersistedUser(id = it) }

    fun createPersistedUser(
        id: Any = stubIdPublisher.publishId(),
        name: String = nameBuilder(id),
        email: String = emailBuilder(id),
        picture: String = pictureBuilder(id),
    ) = userRepository
        .save(
            UserEntity.of(createUser(id.toString(), name, email, picture)),
        ).toDomain()

    private fun nameBuilder(id: Any): String = "name-$id"

    private fun emailBuilder(id: Any): String = "$id@gmail.com"

    private fun pictureBuilder(id: Any): String = "http://server/picture-$id"
}
