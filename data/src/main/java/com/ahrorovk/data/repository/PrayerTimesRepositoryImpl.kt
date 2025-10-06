package com.ahrorovk.data.repository

import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import com.ahrorovk.remote.PrayerTimesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class PrayerTimesRepositoryImpl @Inject constructor(
    private val prayerTimesApi: PrayerTimesApi,
    private val prayTimeDao: PrayTimeDao
) : PrayerTimesRepository {

    override fun getPrayTimesFromDbByDate(date: String): Flow<List<PrayerTimesEntity>> =
        prayTimeDao.getPrayTimesFromDb(date)

    override suspend fun getPrayerTimesByLocation(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double
    ): GetPrayerTimesResponse = prayerTimesApi.getPrayerTimesByLocation(
        year,
        month,
        latitude,
        longitude
    )

    override suspend fun insertPrayTime(prayerTimesEntity: List<PrayerTimesEntity>) =
        prayTimeDao.insert(prayerTimesEntity)

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        address: String,
        school: Int
    ): GetPrayerTimesResponse =
        prayerTimesApi.getPrayerTimes(year, month, address, school)
}