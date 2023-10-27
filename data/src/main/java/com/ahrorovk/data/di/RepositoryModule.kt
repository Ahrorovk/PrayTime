package com.ahrorovk.data.di

import com.ahrorovk.core.HttpRoutes
import com.ahrorovk.data.repository.PrayerTimesRepositoryImpl
import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.remote.PrayerTimesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePrayTimeApi(): PrayerTimesApi =
        Retrofit
            .Builder()
            .baseUrl(HttpRoutes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                (OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                ).build())
            )
            .build()
            .create(PrayerTimesApi::class.java)

    @Provides
    @Singleton
    fun providePrayerTimesRepository(
        prayTimeApi: PrayerTimesApi,
        prayTimeDao: PrayTimeDao
    ): PrayerTimesRepository {
        return PrayerTimesRepositoryImpl(prayTimeApi, prayTimeDao)
    }
}