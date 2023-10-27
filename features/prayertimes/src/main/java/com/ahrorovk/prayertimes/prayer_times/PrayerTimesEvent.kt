package com.ahrorovk.prayertimes.prayer_times

import com.ahrorovk.data.states.GetPrayerTimesState
import java.time.LocalDate

sealed class PrayerTimesEvent {
    data class OnGetPrayerTimesStateChange(val state: GetPrayerTimesState) : PrayerTimesEvent()
    object GetPrayerTimes : PrayerTimesEvent()
    object GetPrayerTimesFromDb : PrayerTimesEvent()
    data class OnDbDateChange(val year: Int, val month: Int, val day: Int) : PrayerTimesEvent()
    data class OnDateChange(val date: LocalDate) : PrayerTimesEvent()
    data class OnDateChangePlus(val state: Long) : PrayerTimesEvent()
    data class OnDateChangeMinus(val state: Long) : PrayerTimesEvent()
}
