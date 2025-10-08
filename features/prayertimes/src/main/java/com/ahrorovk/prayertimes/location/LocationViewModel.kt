package com.ahrorovk.prayertimes.location

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.core.DataStoreManager
import com.ahrorovk.core.Resource
import com.ahrorovk.core.parseCSVtoLocations
import com.ahrorovk.data.states.CurrLocationState
import com.ahrorovk.domain.use_case.location.GetActualLocationUseCase
import com.ahrorovk.domain.use_case.location.GetLocationBySearchUseCase
import com.ahrorovk.domain.use_case.location.GetLocationNameUseCase
import com.ahrorovk.model.dto.toLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getActualLocationUseCase: GetActualLocationUseCase,
    private val dataStoreManager: DataStoreManager,
    private val getLocationNameUseCase: GetLocationNameUseCase,
    private val getLocationBySearchUseCase: GetLocationBySearchUseCase,
    private val context: Application
) : ViewModel() {
    private val _state = MutableStateFlow(LocationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        LocationState()
    )

    init {
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

            is LocationEvent.OnSearchQueryChange -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            searchQuery = event.query
                        )
                    }
                }
            }

            LocationEvent.GetActualLocation -> {
                viewModelScope.launch(Dispatchers.IO) {
                    runBlocking {
                        getActualLocation()
                        getLocationName()
                    }
                }
            }

            LocationEvent.LoadCitiesFromAssets -> {
                loadCitiesFromAssets()
            }

            else -> {

            }
        }
    }

    private fun loadCitiesFromAssets() {
        viewModelScope.launch(Dispatchers.IO) {
            val locations =
                parseCSVtoLocations(context, "worldcities.csv", _state.value.searchQuery)

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
                        dataStoreManager.updateLongitudeState(response.longitude)
                    } else {
                        _state.update {
                            it.copy(
                                currLocationState = CurrLocationState(error = "Response is empty.")
                            )
                        }
                    }
                    Log.e("TAG", "SuccessLocation-> ${_state.value}")
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

    private fun getLocationName() {
        getLocationNameUseCase.invoke(
            _state.value.latitude,
            _state.value.longitude
        ).onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    val response = result.data
                    if (response != null) {
                        _state.update {
                            it.copy(
                                currLocationState = CurrLocationState(response = response.toLocation()),
                                locations = listOf(response.toLocation())
                            )
                        }
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

    private fun getLocationBySearch() {
        getLocationBySearchUseCase.invoke(
            _state.value.searchQuery
        ).onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    val response = result.data
                    if (response != null) {
                        _state.update {
                            it.copy(
                                currLocationState = CurrLocationState(response = response.toLocation()),
                                locations = listOf(response.toLocation())
                            )
                        }
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