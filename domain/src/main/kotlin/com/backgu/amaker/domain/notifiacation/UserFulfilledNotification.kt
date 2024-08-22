package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod
import com.backgu.amaker.domain.user.User

class UserFulfilledNotification(
    val user: User,
    override val method: RealTimeNotificationMethod,
) : RealTimeBasedNotification(method){
    override val keyPrefix: String
        get() = "USER"

    override val keyValue: String
        get() = user.id
}
