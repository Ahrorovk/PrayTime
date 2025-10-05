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
import com.ahrorovk.core.Routes
import com.ahrorovk.core.doesScreenHaveBottomBar
import com.ahrorovk.prayertimes.prayer_times.PrayTimeViewModel
import com.ahrorovk.prayertimes.prayer_times.PrayerTimesEvent
import com.ahrorovk.prayertimes.prayer_times.PrayerTimesScreen
import com.ahrorovk.tasbihfarzun.app.navigation.components.PrayerfulPathBottomBar
import com.ahrorovk.zikr.zikr.ZikrScreen
import com.ahrorovk.zikr.zikr.ZikrViewModel
import com.ahrorovk.core.toCurrentInMillis
import java.time.LocalDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "NewApi")
@Composable
fun Navigation(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentScreen = navController.currentDestination?.route ?: ""
    Scaffold(
        bottomBar = {
            if (doesScreenHaveBottomBar(currentScreen)) {
                PrayerfulPathBottomBar(navController)
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Routes.PrayTimeScreen.route) {
            composable(Routes.ZikrScreen.route) {
                val viewModel = hiltViewModel<ZikrViewModel>()
                val state = viewModel.state.collectAsState()
                ZikrScreen(state = state.value,
                    onEvent = { event ->
                        when (event) {
                            else -> {
                                viewModel.onEvent(event)
                            }
                        }
                    })
            }
            composable(Routes.PrayTimeScreen.route) {
                val viewModel = hiltViewModel<PrayTimeViewModel>()
                val state = viewModel.state.collectAsState()
                LaunchedEffect(key1 = true) {

                    viewModel.onEvent(PrayerTimesEvent.GetPrayerTimes)
                    viewModel.onEvent(
                        PrayerTimesEvent.OnDbDateChange(
                            LocalDate.now().toCurrentInMillis()
                        )
                    )
                }
                LaunchedEffect(key1 = state.value.prayerTimesState.response?.code) {
                    if (state.value.prayerTimesState.response?.code == 200)
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