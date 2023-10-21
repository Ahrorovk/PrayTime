package com.ahrorovk.tasbihfarzun.app.navigation

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrorovk.praytime.praytime.PrayTimeScreen
import com.ahrorovk.praytime.praytime.PrayTimeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                PrayTimeScreen(state = state.value,
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