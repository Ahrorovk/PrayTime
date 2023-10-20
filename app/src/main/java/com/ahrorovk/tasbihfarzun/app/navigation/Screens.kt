package com.ahrorovk.tasbihfarzun.app.navigation



sealed class Screens(val route: String) {
    object PrayTimeScreen : Screens("PrayTimeScreen")
    object TasbihScreen : Screens("TasbihScreen")
}

sealed class BottomSheets(val route: String) {
}