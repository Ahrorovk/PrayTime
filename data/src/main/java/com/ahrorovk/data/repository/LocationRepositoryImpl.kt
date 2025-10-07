package com.ahrorovk.data.repository

import android.Manifest
import android.app.Activity
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
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
    private val context: Context
) : LocationRepository {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun getActualLocation(): Location {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            context
        )
        var location = Location(0.0, 0.0)
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat
                .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return location
        }

        task.addOnSuccessListener {
            if (it != null) {
                location = Location(it.longitude, it.latitude)
                Log.e(
                    "LocationResponse",
                    "Location - > ${location.longitude}  + ${location.latitude}"
                )
            } else {
                Log.e("NULL", "NULL")
            }
        }
        return location
    }

    override suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ): LocationNameResponse = locationApi.getLocationName(latitude, longitude)
}