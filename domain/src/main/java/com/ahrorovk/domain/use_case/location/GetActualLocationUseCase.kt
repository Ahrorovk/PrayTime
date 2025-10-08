package com.ahrorovk.domain.use_case.location

import com.ahrorovk.core.Resource
import com.ahrorovk.domain.repository.LocationRepository
import com.ahrorovk.model.dto.get_prayer_time.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetActualLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<Resource<Location?>> = flow {
        try {
            emit(Resource.Loading<Location?>())
            val response = repository.getActualLocation()
            emit(Resource.Success<Location?>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<Location?>(e.message ?: "Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Location?>(e.message ?: "Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<Location?>("${e.message}"))
        }
    }
}