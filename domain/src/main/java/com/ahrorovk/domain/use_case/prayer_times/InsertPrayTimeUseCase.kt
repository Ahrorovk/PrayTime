package com.ahrorovk.domain.use_case.prayer_times

import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.dto.toPrayTimeEntity
import javax.inject.Inject

class InsertPrayTimeUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    suspend operator fun invoke(prayerTimesDto: PrayerTimesDto){
        repository.insertPrayTime(prayerTimesDto.toPrayTimeEntity())
    }
}