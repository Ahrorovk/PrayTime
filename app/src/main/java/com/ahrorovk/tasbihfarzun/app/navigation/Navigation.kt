package com.ahrorovk.tasbihfarzun.app.navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrorovk.prayertimes.prayer_times.PrayTimeViewModel
import com.ahrorovk.prayertimes.prayer_times.PrayerTimesEvent
import com.ahrorovk.prayertimes.prayer_times.PrayerTimesScreen
import java.time.LocalDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "NewApi")
@Composable
fun Navigation(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    Scaffold {
        NavHost(navController = navController, startDestination = Screens.PrayTimeScreen.route) {
//            composable(Screens.TasbihScreen.route) {
//                val viewModel = hiltViewModel<TasbihViewModel>()
//                val state = viewModel.state.collectAsState()
//                TasbihScreen(state = state.value, onEvent = { event ->
//                    when (event) {
//                        else -> {
//                            viewModel.onEvent(event)
//                        }
//                    }
//                })
//            }
            composable(Screens.PrayTimeScreen.route) {
                val viewModel = hiltViewModel<PrayTimeViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(PrayerTimesEvent.GetPrayerTimes)
                    viewModel.onEvent(PrayerTimesEvent.OnDateChange(LocalDate.now()))
                    viewModel.onEvent(
                        PrayerTimesEvent.OnDbDateChange(
                            LocalDate.now().year,
                            LocalDate.now().monthValue,
                            LocalDate.now().dayOfMonth
                        )
                    )
                    viewModel.onEvent(PrayerTimesEvent.GetPrayerTimesFromDb)
                }
                PrayerTimesScreen(state = state.value,
                    onEvent = { event ->
                        when (event) {
                            else -> viewModel.onEvent(event)
                        }
                    }
                )
            }
        }
    }
}