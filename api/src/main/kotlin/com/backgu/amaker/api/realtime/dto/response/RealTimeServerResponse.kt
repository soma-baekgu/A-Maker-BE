package com.backgu.amaker.api.realtime.dto.response

import com.backgu.amaker.api.realtime.dto.RealTimeServerDto

class RealTimeServerResponse(
    val id: Long,
    val schema: String,
    val address: String,
    val port: Int,
) {
    companion object {
        fun of(realTimeServer: RealTimeServerDto): RealTimeServerResponse =
            RealTimeServerResponse(
                realTimeServer.id,
                realTimeServer.schema,
                realTimeServer.address,
                realTimeServer.port,
            )
    }
}
