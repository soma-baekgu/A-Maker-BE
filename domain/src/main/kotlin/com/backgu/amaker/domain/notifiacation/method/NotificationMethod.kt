package com.backgu.amaker.domain.notifiacation.method

import java.io.Serializable

interface NotificationMethod : Serializable {
    val title: String
    val message: String
}
