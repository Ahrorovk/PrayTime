package com.ahrorovk.prayertimes.prayer_times

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahrorovk.components.CustomProgressIndicator
import com.ahrorovk.components.TimeItem
import com.ahrorovk.components.datePicker.DatePickerLabel
import com.ahrorovk.core.findMinDifference
import com.ahrorovk.core.getListOfTimes
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import showLog
import toMMDDYYYY
import java.time.LocalTime

@SuppressLint("NewApi")
@Composable
fun PrayerTimesScreen(
    state: PrayerTimesState,
    onEvent: (PrayerTimesEvent) -> Unit
) {
    val currentTime = LocalTime.now()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = state.prayerTimesState.error.isNotEmpty()) {
        if (state.prayerTimesState.error.isNotEmpty())
            Toast.makeText(context, state.prayerTimesState.error, Toast.LENGTH_SHORT).show()
    }
    CustomProgressIndicator(isLoading = state.isLoading)
    state.prayerTimes?.let { resp ->
        onEvent(
            PrayerTimesEvent.OnSelectedUpcomingPrayerTimeChange(
                findMinDifference(currentTime,
                    getListOfTimes(resp).map {
                        it.time
                    }
                )
            )
        )
        val listOfTimes = getListOfTimes(resp)
        listOfTimes[state.selectedUpcomingPrayerTimeInd].isTime = true
        onEvent(PrayerTimesEvent.OnUpcomingPrayerTimesChange(listOfTimes))

        LaunchedEffect(key1 = getListOfTimes(resp)) {
            val listOfTimes = getListOfTimes(resp)
            Log.e("LIST_OF_TIMES", "$listOfTimes")
            listOfTimes[state.selectedUpcomingPrayerTimeInd].isTime = true
            onEvent(PrayerTimesEvent.OnUpcomingPrayerTimesChange(listOfTimes))
        }

        LaunchedEffect(key1 = state.dateState) {
            onEvent(PrayerTimesEvent.GetPrayerTimesFromDb)
        }
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = rememberSwipeRefreshState(
                isRefreshing = state.prayerTimesState.isLoading,
            ),
            onRefresh = {
                onEvent(PrayerTimesEvent.GetPrayerTimes)
            }
        ) {
            DatePickerLabel(
                modifier = Modifier.padding(top = 20.dp, start = 12.dp),
                date = state.dateState.toMMDDYYYY(), onDatePick = {
                    onEvent(PrayerTimesEvent.OnDbDateChange(it))
                })
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    items(state.upcomingPrayerTimes) {
                        TimeItem(it) {
                            
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                    }
                }
            }
        }
    }
}