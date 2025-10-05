package com.ahrorovk.domain.repository

import android.content.Context
import com.ahrorovk.model.dto.get_prayer_time.Location
import com.ahrorovk.model.location.LocationNameResponse

interface LocationRepository {
    fun getActualLocation(): Location
    suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ): LocationNameResponse
}