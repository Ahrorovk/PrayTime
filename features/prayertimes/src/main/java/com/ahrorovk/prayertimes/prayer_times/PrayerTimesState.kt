package com.ahrorovk.prayertimes.prayer_times

import android.os.Build
import androidx.annotation.RequiresApi
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
    val selectedMonth: Int = 0,
    val selectedDay: Int = 0,
    val selectedYear: Int = 0,
    val prayerTimes: PrayerTimesEntity? = null,
    val date: LocalDate? = null
)
