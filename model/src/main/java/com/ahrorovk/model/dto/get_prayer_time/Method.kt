package com.ahrorovk.model.dto.get_prayer_time

data class Method(
    val id: Int,
    val location: Location,
    val name: String,
    val params: Params
)