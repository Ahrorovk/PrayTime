package com.ahrorovk.domain.use_case.prayer_times

import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import com.ahrorovk.core.toMMDDYYYY
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPrayerTimesFromDbUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    operator fun invoke(dateState: Long): Flow<List<PrayerTimesEntity>> =
        repository.getPrayTimesFromDbByDate(dateState.toMMDDYYYY())
}