package com.backgu.amaker.domain.notifiacation.type

enum class WorkspaceNotificationType(
    override val title: String,
    override val message: String,
) : NotificationType {
    WORKSPACE_JOINED("워크스페이스 가입", "[%s]에 가입되었습니다."),
    WORKSPACE_INVITED("워크스페이스 초대", "[%s]에 초대되었습니다."),
}
