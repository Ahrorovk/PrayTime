package com.ahrorovk.tasbihfarzun.app.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = BlUE700,
    primaryVariant = Purple700,
    secondary = BLUE300,
    background = Color.Black,
    surface = BLUE400,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = BlUE700,
    primaryVariant = Purple700,
    secondary = BLUE300,
    background = Color.White,
    surface = BLUE400,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)
@Composable
fun TasbihFarzunTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
     val colors = if (darkTheme) {
         DarkColorPalette
     } else {
         LightColorPalette
     }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}