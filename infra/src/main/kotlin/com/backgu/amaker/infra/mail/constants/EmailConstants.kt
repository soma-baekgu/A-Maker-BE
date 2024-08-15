package com.backgu.amaker.infra.mail.constants

enum class EmailConstants(
    val templateName: String,
    val title: String,
    val content: String,
) {
    WORKSPACE_INVITED("workspace-invite", "워크스페이스에 초대되었습니다.", "%s님은 %s 워크스페이스에 초대되었습니다."),
    WORKSPACE_JOINED("workspace-join", "워크스페이스에 가입되었습니다.", "%s님은 %s 워크스페이스에 초대되었습니다."),
    NOT_COMPLETED_NOTIFICATION("event-notification", "미완료된 이벤트", "[%s]가 마감 %s 전입니다. 맡은 업무를 완료해주세요."),
    OVERDUE_NOTIFICATION("event-notification", "마감이 지난 이벤트", "[%s]의 마감 기한이 지났습니다. 맡은 업무를 완료해주세요."),
}
