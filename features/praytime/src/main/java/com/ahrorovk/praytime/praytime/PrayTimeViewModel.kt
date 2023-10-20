package com.ahrorovk.praytime.praytime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class PrayTimeViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(PrayTimeState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        PrayTimeState()
    )

    fun onEvent(event: PrayTimeEvent) {
        when (event) {
            is PrayTimeEvent.OnPrayTimeFajrChange -> {
                _state.update {
                    it.copy(
                        timeOfFajr = event.state
                    )
                }
            }
        }
    }
}