package com.ahrorovk.domain.use_case.prayer_times

import com.ahrorovk.core.dateFormatter
import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.dto.toPrayTimeDto
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPrayerTimesFromDbUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    suspend operator fun invoke(year: Int, month: Int, day: Int): PrayerTimesEntity =
        repository.getPrayTimesFromDbByDate("${dateFormatter(day)}-${dateFormatter(month)}-${dateFormatter(year)}")
}