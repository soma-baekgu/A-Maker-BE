package com.backgu.amaker.api.realtime.dto

import com.backgu.amaker.domain.realtime.RealTimeServer

data class RealTimeServerDto(
    val id: Long,
    val schema: String,
    val address: String,
    val port: Int,
) {
    companion object {
        fun of(realTimeServer: RealTimeServer): RealTimeServerDto =
            RealTimeServerDto(
                realTimeServer.id,
                realTimeServer.schema,
                realTimeServer.address,
                realTimeServer.port,
            )
    }
}
