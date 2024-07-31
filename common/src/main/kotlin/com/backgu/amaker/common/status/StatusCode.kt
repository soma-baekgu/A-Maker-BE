package com.backgu.amaker.api.common.exception

enum class StatusCode(
    val code: String,
    val message: String,
) {
    // OK
    SUCCESS("2000", "성공"),

    // UNAUTHORIZED
    UNAUTHORIZED("4010", "로그인이 필요합니다."),

    // Access Denied
    ACCESS_DENIED("4030", "접근 권한이 없습니다."),

    // INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR("5000", "죄송합니다. 다음에 다시 시도해주세요."),
    RUN_AGAIN_AFTER_AWHILE("4000", "죄송합니다. 잠시후에 다시 시도해주세요."),

    // general
    INVALID_INPUT_VALUE("4000", "잘못된 입력입니다."),

    // user
    USER_NOT_FOUND("4000", "사용자를 찾을 수 없습니다."),

    // auth
    OAUTH_SOCIAL_LOGIN_FAILED("4000", "로그인에 실패했습니다."),

    // chatRoom
    CHAT_ROOM_NOT_FOUND("4000", "채팅방이 존재하지 않습니다."),
    CHAT_ROOM_USER_ALREADY_EXIST("4000", "이미 채팅방에 등록된 사용자입니다."),

    // chatRoomUser
    CHAT_ROOM_USER_NOT_FOUND("4000", "접근할 수 없는 채팅방입니다."),

    // chat
    CHAT_NOT_FOUND("4000", "아직 채팅방의 채팅이 없습니다."),

    // workspace
    WORKSPACE_NOT_FOUND("4000", "워크스페이스를 찾을 수 없습니다."),
    INVALID_WORKSPACE_CREATE("4000", "잘못된 워크스페이스 생성 요청입니다."),
    ALREADY_JOINED_WORKSPACE("4000", "이미 가입된 워크스페이스입니다."),
    INVALID_WORKSPACE_JOIN("4000", "워크스페이스에 가입할 수 없습니다.\n플랜을 업드레이드 하세요"),
    INVALID_WORKSPACE_INVITE("4000", "워크스페이스에 초대할 수 없습니다."),
    ALREADY_JOINED_WORKSPACE("4000", "이미 가입된 워크스페이스입니다."),
    INVALID_WORKSPACE_JOIN("4000", "워크스페이스에 가입할 수 없습니다.\n플랜을 업드레이드 하세요"),

    // workspaceUser
    WORKSPACE_UNREACHABLE("4000", "워크스페이스에 접근할 수 없습니다."),
    WORKSPACE_UNAUTHORIZED("4000", "워크스페이스에 대한 관리자 권한이 없습니다."),

    // event
    EVENT_NOT_FOUND("4000", "이벤트를 찾을 수 없습니다."),

    // eventAssignedUser
    EVENT_ASSIGNED_USER_NOT_FOUND("4000", "접근할 수 없는 이벤트입니다."),
}
