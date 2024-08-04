package com.backgu.amaker.domain.realtime

class RealTimeServer(
    val id: Long,
    val schema: String,
    val address: String,
    val port: Int,
) {
    companion object {
        fun httpOf(
            id: Long,
            address: String,
            port: Int,
        ): RealTimeServer = RealTimeServer(id, "http", address, port)
    }

    fun path(): String = "$schema://$address:$port"
}
