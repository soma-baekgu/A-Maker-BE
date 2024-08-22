package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod

open class WorkspaceNotification(
    open val workspaceId: Long,
    override val method: RealTimeNotificationMethod,
) : RealTimeBasedNotification(method){
    override val keyPrefix: String
        get() = "WORKSPACE"

    override val keyValue: String
        get() = workspaceId.toString()
}
