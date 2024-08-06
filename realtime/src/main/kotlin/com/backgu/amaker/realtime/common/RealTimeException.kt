package com.backgu.amaker.realtime.common

import com.backgu.amaker.common.status.StatusCode

class RealTimeException(
    val statusCode: StatusCode,
) : RuntimeException(statusCode.message)
