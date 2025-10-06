package com.ahrorovk.model.dto.get_prayer_time

data class Location(
    val latitude: Double,
    val longitude: Double,
    val city:String = "",
    val country:String = ""
)