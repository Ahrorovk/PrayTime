package com.ahrorovk.prayertimes.prayer_times

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("NewApi")
@Composable
fun PrayerTimesScreen(
    state: PrayerTimesState,
    onEvent: (PrayerTimesEvent) -> Unit
) {
    val context = LocalContext.current

    val refreshState = rememberSwipeRefreshState(
        isRefreshing = state.prayerTimesState.isLoading,
    )

    LaunchedEffect(key1 = state.prayerTimesState.error.isNotEmpty()) {
        Toast.makeText(context, state.prayerTimesState.error, Toast.LENGTH_SHORT).show()
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            onRefresh = {
                onEvent(PrayerTimesEvent.GetPrayerTimes)
            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    if (!state.prayerTimesState.isLoading)
                        onEvent(PrayerTimesEvent.GetPrayerTimes)
                }) {
                    if (state.prayerTimesState.isLoading)
                        CircularProgressIndicator(modifier = Modifier.background(Color.Red))
                    else
                        Text(text = "Refresh")
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {
                            onEvent(PrayerTimesEvent.OnDateChangeMinus(1))
                            onEvent(PrayerTimesEvent.GetPrayerTimesFromDb)
                        }) {
                            Text(text = "Back")
                        }


                        Button(onClick = {
                            onEvent(PrayerTimesEvent.OnDateChangePlus(1))
                            onEvent(PrayerTimesEvent.GetPrayerTimesFromDb)
                        }) {
                            Text(text = "Next")
                        }
                    }
                }
                state.prayerTimes?.let { resp ->
                    Text(text = resp.fajrTime)
                    Text(text = resp.zuhrTime)
                    Text(text = resp.asrTime)
                    Text(text = resp.magribTime)
                    Text(text = resp.ishaTime)
                    Text(text = resp.date)

                }

            }
        }
    }
}