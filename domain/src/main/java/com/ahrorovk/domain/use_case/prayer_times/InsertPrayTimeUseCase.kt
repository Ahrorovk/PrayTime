package com.ahrorovk.domain.use_case.prayer_times

import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.dto.toPrayTimeEntity
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import kotlinx.coroutines.delay
import javax.inject.Inject

class InsertPrayTimeUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    val prayerTimes = mutableListOf<PrayerTimesEntity>()
    suspend operator fun invoke(prayerTimesDto: List<PrayerTimesDto>) {
        prayerTimesDto.forEach {
            prayerTimes.add(it.toPrayTimeEntity())
        }
        delay(100)
        repository.insertPrayTime(prayerTimes)
    }
}