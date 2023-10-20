package com.ahrorovk.local.dao.pray_time

import androidx.room.Dao
import androidx.room.Query
import com.ahrorovk.local.dao.ext.BaseDao
import com.ahrorovk.model.local.pray_time.PrayTimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayTimeDao : BaseDao<PrayTimeEntity> {

    @Query("SELECT * FROM ${PrayTimeEntity.TABLE_NAME}")
    fun getPrayTimes(): Flow<List<PrayTimeEntity>>
}