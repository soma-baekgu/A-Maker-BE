package com.backgu.amaker.domain.notifiacation

enum class EventNotificationType(
    val title: String,
    val message: String,
) {
    NOT_COMPLETED("미완료된 이벤트", "[%s]가 마감 %s 전입니다. 맡은 업무를 완료해주세요."),
    OVERDUE("마감이 지난 이벤트", "[%s]의 마감 기한이 지났습니다. 맡은 업무를 완료해주세요."),
}
