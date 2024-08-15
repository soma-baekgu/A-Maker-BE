package com.backgu.amaker.domain.user

enum class UserRole(
    var key: String,
    var value: String,
) {
    USER("ROLE_USER", "USER"),
    MANAGER("ROLE_MANAGER", "MANAGER"),
    ADMIN("ROLE_ADMIN", "ADMIN"),
}
