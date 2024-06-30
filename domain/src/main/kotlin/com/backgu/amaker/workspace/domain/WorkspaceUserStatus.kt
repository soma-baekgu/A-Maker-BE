package com.backgu.amaker.workspace.domain

enum class WorkspaceUserStatus(
    var key: String,
    var value: String,
) {
    PENDING("PENDING", "Pending"),
    ACTIVE("ACTIVE", "Active"),
}
