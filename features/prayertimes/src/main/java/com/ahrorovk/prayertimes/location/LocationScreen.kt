package com.ahrorovk.prayertimes.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ahrorovk.components.CityPickerItem

@Composable
fun LocationScreen(
    state: LocationState,
    onEvent: (LocationEvent) -> Unit
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(state.locations.size) { index ->
                val location = state.locations[index]
                CityPickerItem(location = location) {
                    onEvent(LocationEvent.OnSelectedLocation(location))
                }
            }

        }
    }
}