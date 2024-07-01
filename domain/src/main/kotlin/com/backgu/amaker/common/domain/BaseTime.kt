package com.backgu.amaker.common.domain

import java.time.LocalDateTime

abstract class BaseTime(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
