package com.ahrorovk.prayertimes.prayer_times

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ahrorovk.components.CustomProgressIndicator
import com.ahrorovk.components.DailyHadithItem
import com.ahrorovk.components.DatesItem
import com.ahrorovk.components.LocationItem
import com.ahrorovk.components.TimeItem
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

    LaunchedEffect(key1 = state.prayerTimesState.error) {
        if (state.prayerTimesState.error.isNotEmpty()) {
            Log.e("TAG", "DB-> ${state.prayerTimesState.error}")
            Toast.makeText(context, state.prayerTimesState.error, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CustomProgressIndicator(isLoading = true)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!state.isOnline) {
                Text(
                    text = "Offline Mode - Using Cached Data",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error
                )
            }

            state.prayerTimes?.let { resp ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LocationItem(location = state.locationState) { location ->
                        onEvent(PrayerTimesEvent.OnLocationChange(location))
                    }
                    DatesItem(date = resp.date, islamicDate = resp.islamicDate)
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp, bottom = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DailyHadithItem(Modifier, state.hadith.hadith, state.hadith.author)
                }

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    items(state.upcomingPrayerTimes) {
                        TimeItem(it) {
                            // Handle time item click if needed
                        }
                        Spacer(modifier = Modifier.padding(12.dp))
                    }
                }
            }

            if (state.prayerTimes == null && !state.isLoading && state.prayerTimesState.error.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.prayerTimesState.error,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { onEvent(PrayerTimesEvent.GetPrayerTimes) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

