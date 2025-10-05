package com.ahrorovk.prayertimes.prayer_times

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
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
import com.ahrorovk.core.toCurrentInMillis
import com.ahrorovk.core.toMMDDYYYY
import com.ahrorovk.domain.use_case.prayer_times.GetPrayerTimesByLocationUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PrayTimeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getPrayerTimesByLocationUseCase: GetPrayerTimesByLocationUseCase,
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
                getPrayerTimesFromDbUseCase.invoke(
                    _state.value.dateState
                ).onEach { prTm ->
                    if (prTm.isEmpty()) {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(true))
                    } else {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                    }
                    _state.update {
                        it.copy(
                            prayerTimes = prTm
                        )
                    }
                }.launchIn(viewModelScope)
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
        if (_state.value.prayerTimesState.response != null) {
            viewModelScope.launch {
                insertPrayTimeUseCase.invoke(
                    prayerTimesDto
                )
            }
        } else {
            Log.e("TAG", "ResponseNull")
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
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
            getPrayerTimesFromDbUseCase.invoke(_state.value.dateState).onEach { cachedPrayerTimes ->
                if (cachedPrayerTimes != emptyList<PrayerTimesEntity>()) {
                    cachedPrayerTimes.forEach { prTm ->
                        if (prTm.date == _state.value.dateState.toMMDDYYYY()) {
                            _state.update {
                                it.copy(
                                    prayerTimeByDate = prTm,
                                    prayerTimes = cachedPrayerTimes,
                                    isLoading = false
                                )
                            }
                            return@onEach
                        }
                    }
                }
            }.launchIn(viewModelScope)
            delay(100)
            val isOnline = isNetworkAvailable()

            Log.e("TAG", "ONLINE-> $isOnline")
            onEvent(PrayerTimesEvent.OnIsOnlineStateChange(isOnline))

            if (isOnline && _state.value.prayerTimes.isEmpty()) {
                getPrayerTimesFromNetwork()
                delay(100)
                if (_state.value.prayerTimesState.response != null) {
                    getPrayerTimesFromDbUseCase.invoke(_state.value.dateState)
                        .onEach { cachedPrayerTimes ->
                            if (cachedPrayerTimes != emptyList<PrayerTimesEntity>()) {
                                cachedPrayerTimes.forEach { prTm ->
                                    if (prTm.date == _state.value.dateState.toMMDDYYYY()) {
                                        _state.update {
                                            it.copy(
                                                prayerTimeByDate = prTm,
                                                prayerTimes = cachedPrayerTimes,
                                                isLoading = false
                                            )
                                        }
                                        return@onEach
                                    }
                                }
                            }
                        }.launchIn(viewModelScope)
                }
            } else {
                getPrayerTimesFromDbUseCase.invoke(_state.value.dateState)
                    .onEach { cachedPrayerTimes ->

                        if (cachedPrayerTimes != emptyList<PrayerTimesEntity>()) {
                            onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                        } else {
                            onEvent(
                                PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                    GetPrayerTimesState(
                                        error = "No internet connection and no cached data available"
                                    )
                                )
                            )
                            onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                        onEvent(
                            PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                GetPrayerTimesState(
                                    response = response
                                )
                            )
                        )
                        insertPrayerTimes(prayerTimesDto)
                        delay(300)
                        Log.e("TAG", "SUCCESS_INFORMATION-> $response")
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                    }

                    is Resource.Error -> {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                        Log.e("TAG", "PrayTimeFromNetworkError->${result.message}")
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getPrayerTimesByLocationFromNetwork() {
        getPrayerTimesByLocationUseCase.invoke(
            _state.value.dateState.toMMDDYYYY().toMMDDYYYY().year,
            _state.value.dateState.toMMDDYYYY().toMMDDYYYY().monthValue,
            _state.value.latitude,
            _state.value.longitude
        )
            .onEach { result: Resource<GetPrayerTimesResponse> ->
                when (result) {
                    is Resource.Success -> {
                        val response: GetPrayerTimesResponse? = result.data

                        val prayerTimesDto = mutableListOf<PrayerTimesDto>()
                        response?.data?.forEachIndexed { _, data ->
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
                        onEvent(
                            PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                GetPrayerTimesState(
                                    response = response
                                )
                            )
                        )
                        insertPrayerTimes(prayerTimesDto)
                        delay(300)
                        Log.e("TAG", "SUCCESS_INFORMATION-> $response")
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                    }

                    is Resource.Error -> {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                        Log.e("TAG", "PrayTimeFromNetworkError->${result.message}")
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