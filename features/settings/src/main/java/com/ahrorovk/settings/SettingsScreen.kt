package com.ahrorovk.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.settings.components.CommonSettingsCard

@Composable
fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            imageVector = ImageVector.vectorResource(com.ahrorovk.core.R.drawable.background_image),
            contentDescription = "Muslim Wallpaper",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = state.language.name.uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            CommonSettingsCard(
                icon = ImageVector.vectorResource(com.ahrorovk.core.R.drawable.location),
                text = state.
            )
        }
    }
}