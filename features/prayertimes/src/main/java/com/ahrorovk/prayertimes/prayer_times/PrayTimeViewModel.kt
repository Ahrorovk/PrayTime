package com.ahrorovk.prayertimes.prayer_times

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.core.DataStoreManager
import com.ahrorovk.core.Resource
import com.ahrorovk.data.states.GetPrayerTimesState
import com.ahrorovk.domain.use_case.prayer_times.GetPrayerTimesFromDbUseCase
import com.ahrorovk.domain.use_case.prayer_times.GetPrayerTimesUseCase
import com.ahrorovk.domain.use_case.prayer_times.InsertPrayTimeUseCase
import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse
import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import toCurrentInMillis
import toMMDDYYYY
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PrayTimeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val insertPrayTimeUseCase: InsertPrayTimeUseCase,
    private val getPrayerTimesFromDbUseCase: GetPrayerTimesFromDbUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(PrayerTimesState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        PrayerTimesState()
    )
    private var getPrayerTimesJob: Job? = null

    init {
        dataStoreManager.getDateState.onEach { value ->
            _state.update {
                it.copy(
                    dateState = value
                )
            }
        }.launchIn(viewModelScope)
    }

    @SuppressLint("NewApi")
    fun onEvent(event: PrayerTimesEvent) {
        when (event) {
            is PrayerTimesEvent.OnGetPrayerTimesStateChange -> {
                _state.update {
                    it.copy(
                        prayerTimesState = event.state
                    )
                }
            }

            PrayerTimesEvent.GetPrayerTimes -> {
                viewModelScope.launch {
                    dataStoreManager.updateDateState(LocalDate.now().toCurrentInMillis())
                    getPrayerTimes()
                }
            }

            PrayerTimesEvent.GetPrayerTimesFromDb -> {
                getPrayerTimesJob?.cancel()
                viewModelScope.launch {
                    val prayerTimes: PrayerTimesEntity? = getPrayerTimesFromDbUseCase.invoke(
                        _state.value.dateState
                    )
                    if (prayerTimes == null) {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(true))
                    } else {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                    }
                    _state.update {
                        it.copy(
                            prayerTimes = prayerTimes
                        )
                    }
                }
            }

            is PrayerTimesEvent.OnMediaPlayerChange -> {
                _state.update {
                    it.copy(mediaPlayer = event.mediaPlayer)
                }
            }

            is PrayerTimesEvent.OnDbDateChange -> {
                _state.update {
                    it.copy(
                        dateState = event.dateState
                    )
                }
            }

            is PrayerTimesEvent.OnSelectedUpcomingPrayerTimeChange -> {
                _state.update {
                    it.copy(
                        selectedUpcomingPrayerTimeInd = event.ind
                    )
                }
            }

            is PrayerTimesEvent.OnUpcomingPrayerTimesChange -> {
                _state.update {
                    it.copy(
                        upcomingPrayerTimes = event.upcomingPrayerTimes
                    )
                }
            }

            is PrayerTimesEvent.OnIsLoadingStateChange -> {
                _state.update {
                    it.copy(isLoading = event.state)
                }
            }

            is PrayerTimesEvent.OnIsOnlineStateChange -> {
                _state.update {
                    it.copy(isOnline = event.isOnline)
                }
            }

            else -> {}
        }
    }

    private fun insertPrayerTimes(prayerTimesDto: List<PrayerTimesDto>) {
        _state.value.prayerTimesState.response?.let {
            viewModelScope.launch {
                insertPrayTimeUseCase.invoke(
                    prayerTimesDto
                )
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    @SuppressLint("NewApi")
    private fun getPrayerTimes() {
        viewModelScope.launch {
            // First try to get data from Room
            val cachedPrayerTimes = getPrayerTimesFromDbUseCase.invoke(_state.value.dateState)
            val isOnline = isNetworkAvailable()

            onEvent(PrayerTimesEvent.OnIsOnlineStateChange(isOnline))

            if (cachedPrayerTimes != null) {
                // Update UI with cached data first
                _state.update {
                    it.copy(
                        prayerTimes = cachedPrayerTimes,
                        isLoading = false
                    )
                }
            }

            // If we have internet connection, fetch fresh data
            if (isOnline) {
                getPrayerTimesFromNetwork()
            } else {
                // If no internet and we have cached data, use it
                if (cachedPrayerTimes != null) {
                    onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                } else {
                    // No internet and no cached data
                    onEvent(
                        PrayerTimesEvent.OnGetPrayerTimesStateChange(
                            GetPrayerTimesState(
                                error = "No internet connection and no cached data available"
                            )
                        )
                    )
                    onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                }
            }
        }
    }

    private fun getPrayerTimesFromNetwork() {
        getPrayerTimesUseCase.invoke(
            _state.value.dateState.toMMDDYYYY().toMMDDYYYY().year,
            _state.value.dateState.toMMDDYYYY().toMMDDYYYY().monthValue,
            "${_state.value.selectedCity}, ${_state.value.selectedCountry}",
            _state.value.selectedSchool
        )
            .onEach { result: Resource<GetPrayerTimesResponse> ->
                when (result) {
                    is Resource.Success -> {
                        val response: GetPrayerTimesResponse? = result.data

                        val prayerTimesDto = mutableListOf<PrayerTimesDto>()
                        response?.data?.forEachIndexed { _, data ->
                            Log.e("TAG", "SUCCESS_INFORMATION-> $data")
                            prayerTimesDto.add(
                                PrayerTimesDto(
                                    fajrTime = data.timings.Fajr,
                                    zuhrTime = data.timings.Dhuhr,
                                    asrTime = data.timings.Asr,
                                    magribTime = data.timings.Maghrib,
                                    ishaTime = data.timings.Isha,
                                    islamicDate = data.date.hijri.date,
                                    date = data.date.gregorian.date
                                )
                            )
                        }
                        insertPrayerTimes(prayerTimesDto)
                        delay(300)
                        Log.e("TAG", "SUCCESS_INFORMATION-> $response")
                        onEvent(
                            PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                GetPrayerTimesState(
                                    response = response
                                )
                            )
                        )
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                    }

                    is Resource.Error -> {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                        onEvent(
                            PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                GetPrayerTimesState(
                                    error = result.message.toString()
                                )
                            )
                        )
                    }

                    is Resource.Loading -> {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(true))
                        onEvent(
                            PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                GetPrayerTimesState(
                                    isLoading = true
                                )
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}