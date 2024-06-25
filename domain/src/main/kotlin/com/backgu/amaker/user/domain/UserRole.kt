package com.backgu.amaker.user.domain

enum class UserRole(
    var key: String,
    var value: String,
) {
    USER("ROLE_USER", "User"),
    MANAGER("ROLE_MANAGER", "Manager"),
    ADMIN("ROLE_ADMIN", "Admin"),
}
