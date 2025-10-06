package com.ahrorovk.data.states

import com.ahrorovk.model.dto.get_prayer_time.Location

data class CurrLocationState(
    val isLoading: Boolean = false,
    val response: Location? = null,
    val error: String = ""
)
