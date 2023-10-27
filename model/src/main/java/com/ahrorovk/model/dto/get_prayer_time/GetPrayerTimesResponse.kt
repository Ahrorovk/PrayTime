package com.ahrorovk.model.dto.get_prayer_time

data class GetPrayerTimesResponse(
    val code: Int,
    val `data`: List<Data>,
    val status: String
)