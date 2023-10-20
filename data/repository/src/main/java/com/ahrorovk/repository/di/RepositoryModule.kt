package com.ahrorovk.repository.di

import android.annotation.SuppressLint
import com.ahrorovk.core.HttpRoutes
import com.ahrorovk.domain.repository.PrayTimeRepository
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.repository.remote.PrayTimeApi
import com.ahrorovk.repository.repository.PrayTimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@SuppressLint("VisibleForTests")
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providePrayTimeApi(): PrayTimeApi =
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
            .create(PrayTimeApi::class.java)

    @Singleton
    @Provides
    fun providePrayTimeRepository(
        prayTimeApi: PrayTimeApi,
        prayTimeDao: PrayTimeDao
    ): PrayTimeRepository =
        PrayTimeRepositoryImpl(prayTimeApi, prayTimeDao)
}