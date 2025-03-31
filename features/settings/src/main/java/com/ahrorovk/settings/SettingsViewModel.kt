package com.ahrorovk.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.core.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SettingsState()
    )

    init {
        dataStoreManager.getLanguageState.onEach { value ->
            _state.update {
                it.copy(
                    language = _state.value.language.copy(id = value)
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.OnLanguageChange -> {
                viewModelScope.launch {
                    dataStoreManager.updateLanguageState(event.newLanguage.id)
                }
            }
        }
    }
}