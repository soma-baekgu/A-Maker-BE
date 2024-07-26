package com.backgu.amaker.domain.common

import java.time.LocalDateTime

abstract class BaseTime(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
