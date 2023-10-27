package com.ahrorovk.prayertimes.prayer_times

import android.annotation.SuppressLint
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
import java.time.LocalDateTime
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

                getPrayerTimes()
                viewModelScope.launch {
                    dataStoreManager.updateDateState(System.currentTimeMillis())
                }
            }

            PrayerTimesEvent.GetPrayerTimesFromDb -> {
                getPrayerTimesJob?.cancel()

                viewModelScope.launch {
                    val prayerTimes = getPrayerTimesFromDbUseCase.invoke(
                        _state.value.selectedYear,
                        _state.value.selectedMonth,
                        _state.value.selectedDay
                    )
                    Log.e("TAG", "DB\n $prayerTimes")
                    _state.update {
                        it.copy(
                            prayerTimes = prayerTimes
                        )
                    }
                }
            }

            is PrayerTimesEvent.OnDbDateChange -> {
                _state.update {
                    it.copy(
                        selectedYear = event.year,
                        selectedMonth = event.month,
                        selectedDay = event.day
                    )
                }
                Log.e(
                    "TAG",
                    "DATE\n ${_state.value.selectedYear}-${_state.value.selectedMonth}-${_state.value.selectedDay}"
                )
            }

            is PrayerTimesEvent.OnDateChange -> {
                _state.update {
                    it.copy(date = event.date)
                }
            }

            is PrayerTimesEvent.OnDateChangeMinus -> {
                val date = _state.value.date?.minusDays(event.state)
                _state.update {
                    it.copy(date = date)
                }
                val _date = _state.value.date
                Log.e("TAG", "DATEMINUS\n ${_state.value.date}")
                onEvent(
                    PrayerTimesEvent.OnDbDateChange(
                        _date?.year ?: 0,
                        _date?.monthValue ?: 0,
                        _date?.dayOfMonth ?: 0,
                    )
                )
            }

            is PrayerTimesEvent.OnDateChangePlus -> {
                val date = _state.value.date?.plusDays(1)
                _state.update {
                    it.copy(date = date)
                }
                val _date = _state.value.date
                onEvent(
                    PrayerTimesEvent.OnDbDateChange(
                        _date?.year ?: 0,
                        _date?.monthValue ?: 0,
                        _date?.dayOfMonth ?: 0,
                    )
                )
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
        getPrayerTimesUseCase.invoke(
            LocalDateTime.now().year,
            LocalDateTime.now().monthValue,
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
                        response?.data?.forEachIndexed { index, data ->
                            insertPrayerTimes(
                                PrayerTimesDto(
                                    id = index + 1,
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
                        Log.e(
                            "TAG",
                            "GetPrayerTimesResponse->\n ${_state.value.prayerTimesState.response}"
                        )
                    }

                    is Resource.Error -> {
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