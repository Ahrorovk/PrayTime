package com.ahrorovk.prayertimes.prayer_times

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.core.DataStoreManager
import com.ahrorovk.core.Resource
import com.ahrorovk.core.isDateDifferent
import com.ahrorovk.data.states.GetPrayerTimesState
import com.ahrorovk.domain.use_case.prayer_times.GetPrayerTimesFromDbUseCase
import com.ahrorovk.domain.use_case.prayer_times.GetPrayerTimesUseCase
import com.ahrorovk.domain.use_case.prayer_times.InsertPrayTimeUseCase
import com.ahrorovk.model.dto.get_prayer_time.GetPrayerTimesResponse
import com.ahrorovk.model.dto.prayer_times.PrayerTimesDto
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val getPrayerTimesFromDbUseCase: GetPrayerTimesFromDbUseCase
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

                    Log.e(
                        "TAG", "DB\n $prayerTimes ${
                            getPrayerTimesFromDbUseCase.invoke(
                                _state.value.dateState
                            )
                        }"
                    )
                    if (prayerTimes == null) {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(true))
                        getPrayerTimes()
                    } else {
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))
                    }
                    _state.update {
                        it.copy(
                            prayerTimes = prayerTimes
                        )
                    }
                    Log.e(
                        "TAG",
                        "DB\n $prayerTimes ${_state.value.dateState.toMMDDYYYY()}"
                    )
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
                Log.e(
                    "TAG",
                    "DATE\n ${_state.value.dateState.toMMDDYYYY()}"
                )
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
        }
    }

    private fun insertPrayerTimes(prayerTimesDto: PrayerTimesDto) {
        _state.value.prayerTimesState.response?.let {
            viewModelScope.launch {
                insertPrayTimeUseCase.invoke(
                    prayerTimesDto
                )
            }
        }
    }

    @SuppressLint("NewApi")
    private fun getPrayerTimes() {
        Log.e(
            "TAG",
            "getPrayerTimesUseCase->${_state.value.dateState.toMMDDYYYY().toMMDDYYYY().year}"
        )
        getPrayerTimesUseCase.invoke(
            _state.value.dateState.toMMDDYYYY().toMMDDYYYY().year,
            _state.value.dateState.toMMDDYYYY().toMMDDYYYY().monthValue,
            "${_state.value.selectedCity}, ${_state.value.selectedCountry}",
            _state.value.selectedMethod,
            _state.value.selectedSchool
        )
            .onEach { result: Resource<GetPrayerTimesResponse> ->
                when (result) {
                    is Resource.Success -> {
                        val response: GetPrayerTimesResponse? = result.data
                        onEvent(
                            PrayerTimesEvent.OnGetPrayerTimesStateChange(
                                GetPrayerTimesState(
                                    response = response
                                )
                            )
                        )
                        onEvent(PrayerTimesEvent.OnIsLoadingStateChange(false))

                        response?.data?.forEachIndexed { index, data ->
                            if (isDateDifferent(
                                    _state.value.dateState,
                                    _state.value.prayerTimes?.date
                                        ?: _state.value.dateState.toMMDDYYYY()
                                )
                            ) {
                                insertPrayerTimes(
                                    PrayerTimesDto(
                                        id = _state.value.prayerTimes?.id?.plus(index + 1),
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
                        }
                        Log.e(
                            "TAG",
                            "GetPrayerTimesResponse->\n ${_state.value.prayerTimesState.response}"
                        )
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
                        Log.e("TAG", "GetPrayerTimesResponseError->\n ${result.message}")
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