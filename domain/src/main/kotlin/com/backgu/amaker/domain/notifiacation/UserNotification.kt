package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod

open class UserNotification(
    val userId: String,
    override val method: RealTimeNotificationMethod,
) : RealTimeBasedNotification(method)
