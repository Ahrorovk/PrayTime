package com.ahrorovk.model.dto.prayer_times

data class PrayerTimesDto(
    val id: Int?,
    val fajrTime: String,
    val zuhrTime: String,
    val asrTime: String,
    val magribTime: String,
    val ishaTime: String,
    val islamicDate: String,
    val date: String
)
