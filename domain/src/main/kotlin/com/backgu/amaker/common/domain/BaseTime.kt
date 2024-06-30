package com.backgu.amaker.common.domain

import java.time.LocalDateTime

open class BaseTime(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
