package com.ahrorovk.praytime.praytime

sealed class PrayTimeEvent {
    data class OnPrayTimeFajrChange(val state: Long) : PrayTimeEvent()
}
