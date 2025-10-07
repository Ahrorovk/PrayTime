package com.ahrorovk.tasbihfarzun.app.navigation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ahrorovk.core.R
import com.ahrorovk.core.Routes
import com.ahrorovk.model.navigation.BottomNavDestination

@Composable
fun PrayerfulPathBottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        bottomNavDestinations.forEach { navItem ->
            BottomNavItem(navController = navController, item = navItem)
        }

    }
}

val bottomNavDestinations = listOf(
    BottomNavDestination(
        label = "Time",
        destinationRoute = Routes.PrayTimeScreen.route,
        icon = R.drawable.__access_time
    ),
    BottomNavDestination(
        label = "Zikr",
        destinationRoute = Routes.ZikrScreen.route,
        icon = R.drawable.rosary
    ),
    BottomNavDestination(
        label = "Quran",
        destinationRoute = Routes.QuranScreen.route,
        icon = R.drawable.quran
    ),
    BottomNavDestination(
        label = "Settings",
        destinationRoute = Routes.SettingsScreen.route,
        icon = R.drawable.settings
    )
)