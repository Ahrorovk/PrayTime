package com.ahrorovk.domain.use_case.prayer_times

import com.ahrorovk.core.Resource
import com.ahrorovk.domain.repository.PrayerTimesRepository
import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetPrayerTimesUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    operator fun invoke(
        year: Int,
        month: Int,
        address: String,
        school: Int
    ): Flow<Resource<GetPrayerTimesResponse>> =
        flow {
            try {
                emit(Resource.Loading<GetPrayerTimesResponse>())
                val response = repository.getPrayerTimes(year, month, address, school)
                emit(Resource.Success<GetPrayerTimesResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetPrayerTimesResponse>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<GetPrayerTimesResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<GetPrayerTimesResponse>("${e.message}"))
            }
        }
}