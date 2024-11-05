package com.ahrorovk.local.dao.pray_time

import androidx.room.Dao
import androidx.room.Query
import com.ahrorovk.local.dao.ext.BaseDao
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayTimeDao : BaseDao<PrayerTimesEntity> {
    @Query("SELECT * FROM ${PrayerTimesEntity.TABLE_NAME} WHERE date=:date")
    suspend fun getPrayTimesFromDb(date:String): PrayerTimesEntity?
}