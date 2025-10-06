package com.ahrorovk.prayertimes.location

import com.ahrorovk.data.states.CurrLocationState
import com.ahrorovk.model.dto.get_prayer_time.Location

data class LocationState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val selectedLocation: Location? = null,
    val currLocationState: CurrLocationState = CurrLocationState(),
    val locations: List<Location> = emptyList()
)
