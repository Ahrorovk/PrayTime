package com.ahrorovk.model.local.pray_time

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PrayTimeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_FAJR_TIME) val fajrTime: Long,
    @ColumnInfo(name = COLUMN_ZUHR_TIME) val zuhrTime: Long,
    @ColumnInfo(name = COLUMN_ASR_TIME) val asrTime: Long,
    @ColumnInfo(name = COLUMN_MAGRIB_TIME) val magribTime: Long,
    @ColumnInfo(name = COLUMN_ISHA_TIME) val ishaTime: Long,
) {
    companion object {
        const val TABLE_NAME = "pray_time_table"
        const val COLUMN_ID = "id"
        const val COLUMN_FAJR_TIME = "fajr_time"
        const val COLUMN_ZUHR_TIME = "zuhr_time"
        const val COLUMN_ASR_TIME = "asr_time"
        const val COLUMN_MAGRIB_TIME = "magrib_time"
        const val COLUMN_ISHA_TIME = "isha_time"
    }
}
