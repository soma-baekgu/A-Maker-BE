package com.backgu.amaker.infra.mail.constants

enum class EmailConstants(
    val templateName: String,
    val title: String,
    val content: String,
) {
    WORKSPACE_INVITED("workspace-invite", "워크스페이스에 초대되었습니다.", "%s님은 %s 워크스페이스에 초대되었습니다."),
    WORKSPACE_JOINED("workspace-join", "워크스페이스에 가입되었습니다.", "%s님은 %s 워크스페이스에 초대되었습니다."),
}
