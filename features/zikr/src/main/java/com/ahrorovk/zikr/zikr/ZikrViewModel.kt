package com.ahrorovk.zikr.zikr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ZikrViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(ZikrState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        ZikrState()
    )

    fun onEvent(event: ZikrEvent) {
        when (event) {
            else -> Unit
        }
    }
}