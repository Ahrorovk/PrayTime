package com.ahrorovk.model.dto

import com.ahrorovk.model.dto.pray_time.PrayTimeDto
import com.ahrorovk.model.local.pray_time.PrayTimeEntity

fun PrayTimeEntity.toPrayTimeDto() = PrayTimeDto(
    id = id,
    fajrTime = fajrTime,
    zuhrTime = zuhrTime,
    asrTime = asrTime,
    magribTime = magribTime,
    ishaTime = ishaTime
)
fun PrayTimeDto.toPrayTimeEntity() = PrayTimeEntity(
    id = id,
    fajrTime = fajrTime,
    zuhrTime = zuhrTime,
    asrTime = asrTime,
    magribTime = magribTime,
    ishaTime = ishaTime
)