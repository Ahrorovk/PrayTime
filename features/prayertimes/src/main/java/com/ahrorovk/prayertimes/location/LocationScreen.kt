package com.ahrorovk.prayertimes.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahrorovk.components.CityPickerItem
import com.ahrorovk.model.dto.toLocationName

@Composable
fun LocationScreen(
    state: LocationState,
    onEvent: (LocationEvent) -> Unit
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                TextField(
                    value = state.searchQuery,
                    onValueChange = {
                        onEvent(LocationEvent.OnSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = {
                        Text(text = "Search city")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        backgroundColor = Color.Transparent
                    )
                )
                Button({onEvent(LocationEvent.LoadCitiesFromAssets)}) { }

                Text("Current Location",Modifier.clickable {
                    onEvent(LocationEvent.GetActualLocation)
                })
            }
            itemsIndexed(state.locations.sortedBy { it.country }) { ind, location ->
                CityPickerItem(location = location) {
                    onEvent(LocationEvent.OnSelectedLocation(location))
                }
            }
        }
    }
}