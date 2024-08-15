package com.backgu.amaker.domain.user

class UserDevice(
    val id: Long = 0L,
    val userId: String,
    val device: Device,
    val token: String,
)
