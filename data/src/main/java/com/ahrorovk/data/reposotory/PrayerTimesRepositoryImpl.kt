package com.ahrorovk.data.repository

import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import com.ahrorovk.remote.PrayerTimesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class PrayerTimesRepositoryImpl(
    private val prayerTimesApi: PrayerTimesApi,
    private val prayTimeDao: PrayTimeDao
) : PrayerTimesRepository {

    override suspend fun getPrayTimesFromDbByDate(date: String): PrayerTimesEntity =
        prayTimeDao.getPrayTimesFromDb(date)

    override suspend fun insertPrayTime(prayerTimesEntity: PrayerTimesEntity) =
        prayTimeDao.insert(prayerTimesEntity)

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        address: String,
        method: Int,
        school: Int
    ): GetPrayerTimesResponse =
        prayerTimesApi.getPrayerTimes(year, month, address, method, school)
}