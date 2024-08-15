package com.backgu.amaker.infra.common.clock

import com.backgu.amaker.common.clock.ClockHolder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Component
class SystemClockHolder : ClockHolder {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+09:00")
    }

    override fun now(): String {
        val now = LocalDateTime.now()
        return now.atZone(ZoneOffset.UTC).format(formatter)
    }
}
