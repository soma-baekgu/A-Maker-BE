package com.backgu.amaker.infra.jpa.user.entity

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.user.UserRole
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "User")
@Table(name = "users")
class UserEntity(
    @Id
    var id: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false, unique = true)
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
