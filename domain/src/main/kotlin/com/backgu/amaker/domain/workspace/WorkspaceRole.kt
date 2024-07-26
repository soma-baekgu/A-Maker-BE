package com.backgu.amaker.domain.workspace

enum class WorkspaceRole(
    var key: String,
    var value: String,
) {
    MEMBER("ROLE_MEMBER", "Member"),
    LEADER("ROLE_LEADER", "Leader"),
}
