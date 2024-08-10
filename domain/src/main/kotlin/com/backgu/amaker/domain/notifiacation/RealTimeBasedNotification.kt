package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod

abstract class RealTimeBasedNotification(
    override val method: RealTimeNotificationMethod,
) : Notification
