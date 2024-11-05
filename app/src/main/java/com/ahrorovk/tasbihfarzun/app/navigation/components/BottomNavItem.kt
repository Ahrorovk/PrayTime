package com.ahrorovk.tasbihfarzun.app.navigation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ahrorovk.core.Routes
import com.ahrorovk.model.navigation.BottomNavDestination

@Composable
fun RowScope.BottomNavItem(
    navController: NavController,
    item: BottomNavDestination
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            when (item.destinationRoute) {
                Routes.PrayTimeScreen.route -> {
                    it.route == Routes.PrayTimeScreen.route
                }

                Routes.ZikrScreen.route -> {
                    it.route == Routes.ZikrScreen.route
                }


                Routes.QuranScreen.route -> {
                    it.route == Routes.QuranScreen.route
                }

                Routes.SettingsScreen.route -> {
                    it.route == Routes.SettingsScreen.route
                }

                else -> {
                    it.route == Routes.PrayTimeScreen.route
                }
            }
        } == true,
        onClick = {
            if (currentDestination?.route != item.destinationRoute)
                navigateToScreen(item.destinationRoute, navController)
        },
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = item.icon),
                contentDescription = "BottomNavIcon"
            )
        },
        selectedContentColor = MaterialTheme.colors.surface,
        unselectedContentColor = MaterialTheme.colors.onBackground
    )
}

private fun navigateToScreen(route: String, navController: NavController) {
    navController.navigate(route = route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}