package com.backgu.amaker.user.jpa

import com.backgu.amaker.common.jpa.BaseTimeEntity
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.domain.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity(name = "User")
@Table(name = "users")
class UserEntity(
    @Id
    var id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    var picture: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val userRole: UserRole = UserRole.USER,
) : BaseTimeEntity() {
    fun toDomain(): User =
        User(
            id = id,
            name = name,
            email = email,
            picture = picture,
            userRole = userRole,
        )

    companion object {
        fun of(userEntity: User): UserEntity =
            UserEntity(
                id = userEntity.id,
                name = userEntity.name,
                email = userEntity.email,
                picture = userEntity.picture,
                userRole = userEntity.userRole,
            )
    }
}
