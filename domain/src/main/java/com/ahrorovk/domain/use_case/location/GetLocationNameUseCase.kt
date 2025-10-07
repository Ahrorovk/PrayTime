package com.ahrorovk.domain.use_case.location

import com.ahrorovk.core.Resource
import com.ahrorovk.domain.repository.LocationRepository
import com.ahrorovk.model.location.LocationNameResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLocationNameUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(lat: Double, lon: Double): Flow<Resource<LocationNameResponse>> =
        flow {
            try {
                emit(Resource.Loading<LocationNameResponse>())
                val response = repository.getLocationName(lat, lon)
                emit(Resource.Success<LocationNameResponse>(response))
            } catch (e: HttpException) {
                emit(Resource.Error<LocationNameResponse>(e.message ?: "Error"))
            } catch (e: IOException) {
                emit(Resource.Error<LocationNameResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<LocationNameResponse>("${e.message}"))
            }
        }
}