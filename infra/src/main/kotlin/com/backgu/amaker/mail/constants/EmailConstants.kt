package com.backgu.amaker.mail.constants

enum class EmailConstants(
    val templateName: String,
    val title: String,
) {
    WORKSPACE_INVITED("workspace-invite", "%s님은 워크스페이스에 초대되었습니다."),
    WORKSPACE_JOINED("workspace-join", "%s님은 워크스페이스에 가입되었습니다."),
}
