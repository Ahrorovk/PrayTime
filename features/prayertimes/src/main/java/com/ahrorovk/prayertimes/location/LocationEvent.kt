package com.ahrorovk.prayertimes.location

import com.ahrorovk.model.dto.get_prayer_time.Location

sealed class LocationEvent {
    data class OnSelectedLocation(val location: Location) : LocationEvent()
    object GoBack : LocationEvent()
}