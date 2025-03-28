package com.ahrorovk.domain.repository

import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import kotlinx.coroutines.flow.Flow

interface PrayerTimesRepository {
    suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        address: String,
        school: Int
    ): GetPrayerTimesResponse

    suspend fun insertPrayTime(prayerTimesEntity: List<PrayerTimesEntity>)
    suspend fun getPrayTimesFromDbByDate(date:String): PrayerTimesEntity?
}