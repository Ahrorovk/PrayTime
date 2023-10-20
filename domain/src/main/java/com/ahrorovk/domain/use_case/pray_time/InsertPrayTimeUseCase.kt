package com.ahrorovk.domain.use_case.pray_time

import com.ahrorovk.model.dto.pray_time.PrayTimeDto
import com.ahrorovk.model.dto.toPrayTimeEntity
import com.ahrorovk.repository.repository.PrayTimeRepositoryImpl
import javax.inject.Inject

class InsertPrayTimeUseCase @Inject constructor(
    private val repositoryImpl: PrayTimeRepositoryImpl
) {
    suspend operator fun invoke(prayTimeDto: PrayTimeDto){
        repositoryImpl.insertPrayTime(prayTimeDto.toPrayTimeEntity())
    }
}