package com.backgu.amaker.user.domain

import com.backgu.amaker.common.domain.BaseTime
import com.backgu.amaker.workspace.domain.Workspace

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
}
