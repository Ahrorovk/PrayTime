package com.ahrorovk.model.dto.pray_time

data class PrayTimeDto(
    val id: Int?,
    val fajrTime: Long,
    val zuhrTime: Long,
    val asrTime: Long,
    val magribTime: Long,
    val ishaTime: Long
)
