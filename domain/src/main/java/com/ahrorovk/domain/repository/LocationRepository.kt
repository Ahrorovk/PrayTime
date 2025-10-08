package com.ahrorovk.domain.repository

import com.ahrorovk.model.dto.get_prayer_time.Location
import com.ahrorovk.model.location.LocationNameResponse

interface LocationRepository {
    suspend fun getActualLocation(): Location?
    suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ): LocationNameResponse
    suspend fun getLocationBySearch(
        city:String
    ): LocationNameResponse
}