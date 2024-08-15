package com.backgu.amaker.application.notification.event

import com.backgu.amaker.domain.notifiacation.Notification

interface NotificationEventWithCallback {
    val notification: Notification

    fun preHandle() {}

    fun postHandle() {}

    fun onFail(exception: Exception) {}
}
