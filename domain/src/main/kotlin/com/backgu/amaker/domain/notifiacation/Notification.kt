package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import java.io.Serializable

interface Notification : Serializable {
    val method: NotificationMethod
}
