package com.ahrorovk.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity

@Database(
    entities = [PrayerTimesEntity::class],
    version = 7,
    exportSchema = false
)
abstract class PrayTimeDatabase : RoomDatabase() {
    abstract fun prayTimeDao(): PrayTimeDao
}