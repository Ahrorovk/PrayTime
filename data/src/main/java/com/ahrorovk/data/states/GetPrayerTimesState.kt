package com.ahrorovk.data.states

import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse

data class GetPrayerTimesState(
    var isLoading: Boolean = false,
    var response: GetPrayerTimesResponse? = null,
    val error: String = ""
)
