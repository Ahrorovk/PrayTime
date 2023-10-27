package com.ahrorovk.local.di

import android.content.Context
import androidx.room.Room
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.local.db.PrayTimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private const val DB_NAME = "pray_time_db"

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    @Named(value = DB_NAME)
    fun provideDatabaseName(): String {
        return "pray_time_db"//BuildConfig.DB_NAME
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @Named(value = DB_NAME) dbname: String,
        @ApplicationContext context: Context
    ): PrayTimeDatabase {
        return Room.databaseBuilder(context, PrayTimeDatabase::class.java, dbname)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePrayerTimesDao(appDatabase: PrayTimeDatabase): PrayTimeDao {
        return appDatabase.prayTimeDao()
    }
}