package com.backgu.amaker.domain.workspace

enum class WorkspaceUserStatus(
    var key: String,
    var value: String,
) {
    PENDING("PENDING", "Pending"),
    ACTIVE("ACTIVE", "Active"),
}
