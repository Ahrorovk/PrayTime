package com.ahrorovk.prayertimes.prayer_times

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import com.ahrorovk.core.model.Time
import com.ahrorovk.data.states.GetPrayerTimesState
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import java.time.LocalDate

data class PrayerTimesState(
    val prayerTimesState: GetPrayerTimesState = GetPrayerTimesState(),
    val selectedCity: String = "Khujand",
    val selectedCountry: String = "Tajikistan",
    val selectedMethod: Int = 1,
    val selectedSchool: Int = 1,
    val dateState: Long = 0,
    val isLoading: Boolean = false,
    val prayerTimes: PrayerTimesEntity? = null,
    val mediaPlayer: MediaPlayer? = null,
    val selectedUpcomingPrayerTimeInd: Int = 0,
    val upcomingPrayerTimes: List<Time> = emptyList()
)
