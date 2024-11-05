package com.ahrorovk.prayertimes.prayer_times

import android.media.MediaPlayer
import com.ahrorovk.core.model.Time
import com.ahrorovk.data.states.GetPrayerTimesState
import java.time.LocalDate

sealed class
PrayerTimesEvent {
    data class OnGetPrayerTimesStateChange(val state: GetPrayerTimesState) : PrayerTimesEvent()
    data class OnMediaPlayerChange(val mediaPlayer: MediaPlayer) : PrayerTimesEvent()
    data class OnSelectedUpcomingPrayerTimeChange(val ind: Int) : PrayerTimesEvent()
    data class OnUpcomingPrayerTimesChange(val upcomingPrayerTimes: List<Time>) : PrayerTimesEvent()
    data class OnIsLoadingStateChange(val state: Boolean) : PrayerTimesEvent()
    object GetPrayerTimes : PrayerTimesEvent()
    object GetPrayerTimesFromDb : PrayerTimesEvent()
    data class OnDbDateChange(val dateState: Long) : PrayerTimesEvent()
}