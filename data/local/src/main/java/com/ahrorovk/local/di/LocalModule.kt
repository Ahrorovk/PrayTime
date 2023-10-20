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

private const val DB_NAME = "db_name"

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
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
            .createFromAsset("$dbname.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(appDatabase: PrayTimeDatabase): PrayTimeDao {
        return appDatabase.prayTimeDao()
    }
}