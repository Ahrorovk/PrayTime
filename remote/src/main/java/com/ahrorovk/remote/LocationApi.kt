package com.ahrorovk.remote

import com.ahrorovk.model.location.LocationNameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("/data/reverse-geocode-client")
    suspend fun getLocationName(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): LocationNameResponse
}