package com.backgu.amaker.domain.realtime

class RealTimeServer(
    val id: String,
    val schema: String,
    val address: String,
    val port: Int,
) {
    companion object {
        fun httpOf(
            id: String,
            address: String,
            port: Int,
        ): RealTimeServer = RealTimeServer(id, "http", address, port)
    }

    fun path(): String = "$schema://$address:$port"
}
