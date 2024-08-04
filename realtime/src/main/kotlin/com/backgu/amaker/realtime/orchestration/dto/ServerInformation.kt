package com.backgu.amaker.realtime.orchestration.dto

data class ServerInformation(
    val id: Long,
    val schema: String,
    val address: String,
    val port: Int,
)
