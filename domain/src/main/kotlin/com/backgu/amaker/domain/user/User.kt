package com.backgu.amaker.domain.user

import com.backgu.amaker.domain.common.BaseTime
import com.backgu.amaker.domain.workspace.Workspace

class User(
    val id: String,
    val name: String,
    val email: String,
    val picture: String,
    val userRole: UserRole = UserRole.USER,
) : BaseTime() {
    fun createWorkspace(name: String): Workspace {
        val workspace = Workspace(name = name)
        return workspace
    }

    fun isNonInvitee(invitee: User) = email != invitee.email

    companion object {
        fun of(
            id: String,
            name: String,
            email: String,
            picture: String,
            userRole: UserRole = UserRole.USER,
        ): User =
            User(
                id = id,
                name = name,
                email = email,
                picture = picture,
                userRole = userRole,
            )
    }
}
