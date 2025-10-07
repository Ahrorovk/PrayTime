package com.ahrorovk.model.dto

import com.ahrorovk.model.dto.get_prayer_time.Location
import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import com.ahrorovk.model.location.LocationNameResponse

fun PrayerTimesEntity.toPrayTimeDto() = PrayerTimesDto(
    id = id,
    fajrTime = fajrTime,
    zuhrTime = zuhrTime,
    asrTime = asrTime,
    magribTime = magribTime,
    ishaTime = ishaTime,
    islamicDate = islamicDate,
    date = date
)

fun PrayerTimesDto.toPrayTimeEntity() = PrayerTimesEntity(
    id = id,
    fajrTime = fajrTime,
    zuhrTime = zuhrTime,
    asrTime = asrTime,
    magribTime = magribTime,
    ishaTime = ishaTime,
    islamicDate = islamicDate,
    date = date
)

fun LocationNameResponse.toLocation() = Location(
    city = city,
    country = countryName,
    latitude = latitude,
    longitude = longitude
)

fun List<LocationNameResponse>.toLocationList() = this.map { it.toLocation() }

fun List<PrayerTimesEntity>.toPrayTimeDtoList() = this.map { it.toPrayTimeDto() }

fun List<PrayerTimesDto>.toPrayTimeEntityList() = this.map { it.toPrayTimeEntity() }

fun List<Location>.toLocationNameList() = this.map { "${it.city}, ${it.country}" }

fun Location.toLocationName() = "${this.city}, ${this.country}"

fun List<Location>.toLocationNameSet() = this.map { "${it.city}, ${it.country}" }.toSet()

fun List<Location>.findLocationByName(name: String) = this.find { "${it.city}, ${it.country}" == name }

fun List<Location>.findLocationsByCoordinates(lat: Double, lon: Double) =
    this.find { it.latitude == lat && it.longitude == lon }

fun Location.findLocationByCoordinates(lat: Double, lon: Double) =
    this.takeIf { it.latitude == lat && longitude == lon }
