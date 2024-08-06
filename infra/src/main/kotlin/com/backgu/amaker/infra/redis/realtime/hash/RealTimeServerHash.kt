package com.backgu.amaker.infra.redis.realtime.hash

import com.backgu.amaker.domain.realtime.RealTimeServer
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("realtime")
class RealTimeServerHash(
    @Id
    val id: String,
    val schema: String,
    val address: String,
    val port: Int,
) {
    fun toDomain(): RealTimeServer = RealTimeServer(id, schema, address, port)

    companion object {
        fun of(realTimeServer: RealTimeServer): RealTimeServerHash =
            RealTimeServerHash(realTimeServer.id, realTimeServer.schema, realTimeServer.address, realTimeServer.port)
    }
}
