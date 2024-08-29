package com.backgu.amaker.realtime.common.excpetion

import com.backgu.amaker.common.status.StatusCode

class RealTimeException(
    statusCode: StatusCode,
) : RuntimeException(statusCode.message)
