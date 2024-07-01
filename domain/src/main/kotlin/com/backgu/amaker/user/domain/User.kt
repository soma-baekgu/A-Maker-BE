package com.backgu.amaker.user.domain

import com.backgu.amaker.common.domain.BaseTime
import com.backgu.amaker.workspace.domain.Workspace

class User(
    var id: String = "",
    var name: String,
    val email: String,
    var picture: String,
    val userRole: UserRole = UserRole.USER,
) : BaseTime() {
    fun createWorkspace(name: String): Workspace {
        val workspace = Workspace(name = name)
        return workspace
    }
}
