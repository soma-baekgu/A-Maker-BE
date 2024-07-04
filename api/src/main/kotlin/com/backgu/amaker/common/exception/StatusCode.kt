package com.backgu.amaker.common.exception

enum class StatusCode(
    val code: String,
    val message: String,
) {
    // OK
    SUCCESS("2000", "성공"),

    // general
    INVALID_INPUT_VALUE("4000", "잘못된 입력입니다."),

    // user
    USER_NOT_FOUND("4000", "사용자를 찾을 수 없습니다."),

    // auth
    OAUTH_SOCIAL_LOGIN_FAILED("4000", "로그인에 실패했습니다."),

    // chat
    CHAT_ROOM_NOT_FOUND("4000", "채팅방이 존재하지 않습니다."),

    // workspace
    WORKSPACE_NOT_FOUND("4000", "워크스페이스를 찾을 수 없습니다."),

    // workspaceUser
    WORKSPACE_UNREACHABLE("4000", "워크스페이스에 접근할 수 없습니다."),
}
