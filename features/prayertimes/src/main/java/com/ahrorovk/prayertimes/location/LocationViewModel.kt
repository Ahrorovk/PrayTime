package com.ahrorovk.prayertimes.location

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.core.DataStoreManager
import com.ahrorovk.core.Resource
import com.ahrorovk.core.parseCSVtoLocations
import com.ahrorovk.data.states.CurrLocationState
import com.ahrorovk.domain.use_case.location.GetActualLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getActualLocationUseCase: GetActualLocationUseCase,
    private val dataStoreManager: DataStoreManager,
    private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(LocationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        LocationState()
    )

    init {
        loadCitiesFromAssets()
        dataStoreManager.getLatitudeState.onEach { value ->
            _state.update {
                it.copy(
                    latitude = value
                )
            }
        }.launchIn(viewModelScope)

        dataStoreManager.getLongitudeState.onEach { value ->
            _state.update {
                it.copy(
                    longitude = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: LocationEvent) {
        when (event) {
            is LocationEvent.OnSelectedLocation -> {
                _state.update {
                    it.copy(
                        selectedLocation = event.location
                    )
                }
            }

            else -> {

            }
        }
    }

    private fun loadCitiesFromAssets() {
        viewModelScope.launch(Dispatchers.IO) {
            val locations = parseCSVtoLocations(context, "worldcities.csv")

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        locations = locations
                    )
                }
            }
        }
    }

    private fun getActualLocation() {
        getActualLocationUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    val response = result.data
                    if (response != null) {
                        _state.update {
                            it.copy(
                                currLocationState = CurrLocationState(response = response)
                            )
                        }
                        dataStoreManager.updateLatitudeState(response.latitude)
                        dataStoreManager.updateLatitudeState(response.longitude)
                    } else {
                        _state.update {
                            it.copy(
                                currLocationState = CurrLocationState(error = "Response is empty.")
                            )
                        }
                    }
                }

                is Resource.Error<*> -> {
                    _state.update {
                        it.copy(
                            currLocationState = CurrLocationState(
                                error = result.message ?: "Some error"
                            )
                        )
                    }
                }

                is Resource.Loading<*> -> {
                    _state.update {
                        it.copy(
                            currLocationState = CurrLocationState(isLoading = true)
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}