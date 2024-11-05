package com.ahrorovk.domain.use_case.prayer_times

import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import toMMDDYYYY
import javax.inject.Inject

class GetPrayerTimesFromDbUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    suspend operator fun invoke(dateState: Long): PrayerTimesEntity? =
        repository.getPrayTimesFromDbByDate(dateState.toMMDDYYYY())
}