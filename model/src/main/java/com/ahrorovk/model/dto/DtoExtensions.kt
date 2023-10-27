package com.ahrorovk.model.dto

import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity

fun PrayerTimesEntity.toPrayTimeDto() = PrayerTimesDto(
    id = id,
    fajrTime = fajrTime,
    zuhrTime = zuhrTime,
    asrTime = asrTime,
    magribTime = magribTime,
    ishaTime = ishaTime,
    islamicDate = islamicDate,
    date = date
)

fun PrayerTimesDto.toPrayTimeEntity() = PrayerTimesEntity(
    id = id,
    fajrTime = fajrTime,
    zuhrTime = zuhrTime,
    asrTime = asrTime,
    magribTime = magribTime,
    ishaTime = ishaTime,
    islamicDate = islamicDate,
    date = date
)