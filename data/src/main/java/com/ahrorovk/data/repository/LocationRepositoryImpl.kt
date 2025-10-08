package com.ahrorovk.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.ahrorovk.domain.repository.LocationRepository
import com.ahrorovk.model.dto.get_prayer_time.Location
import com.ahrorovk.model.location.LocationNameResponse
import com.ahrorovk.remote.LocationApi
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
    private val context: Application
) : LocationRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getActualLocation(): Location? {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        return suspendCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(Location(location.latitude,location.longitude))
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }


    override suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ): LocationNameResponse = locationApi.getLocationName(latitude, longitude)

    override suspend fun getLocationBySearch(city: String): LocationNameResponse =
        locationApi.getLocationBySearch(city)
}