package com.ahrorovk.prayertimes.prayer_times

import android.media.MediaPlayer
import com.ahrorovk.core.model.Hadith
import com.ahrorovk.core.model.Time
import com.ahrorovk.data.states.GetPrayerTimesState
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity

data class PrayerTimesState(
    val prayerTimesState: GetPrayerTimesState = GetPrayerTimesState(),
    val selectedCity: String = "Khujand",
    val selectedCountry: String = "Tajikistan",
    val selectedMethod: Int = 1,
    val selectedSchool: Int = 1,
    val dateState: Long = 0,
    val locationState: String = "Location",
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
    val prayerTimes: PrayerTimesEntity? = null,
    val mediaPlayer: MediaPlayer? = null,
    val selectedUpcomingPrayerTimeInd: Int = 0,
    val upcomingPrayerTimes: List<Time> = emptyList(),
    val hadith: Hadith = Hadith()
)
