package com.backgu.amaker.application.notification.event

interface NotificationEventWithCallback {
    val notificationEvent: NotificationEvent

    fun preHandle() {}

    fun postHandle() {}

    fun onFail(exception: Exception) {}
}
