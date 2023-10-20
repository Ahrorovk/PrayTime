package com.ahrorovk.repository.repository

import com.ahrorovk.domain.repository.PrayTimeRepository
import com.ahrorovk.local.dao.pray_time.PrayTimeDao
import com.ahrorovk.model.local.pray_time.PrayTimeEntity
import com.ahrorovk.repository.remote.PrayTimeApi

class PrayTimeRepositoryImpl(
    private val prayTimeApi: PrayTimeApi,
    private val prayTimeDao: PrayTimeDao
) : PrayTimeRepository {

    suspend fun insertPrayTime(prayTimeEntity: PrayTimeEntity) = prayTimeDao.insert(prayTimeEntity)
}