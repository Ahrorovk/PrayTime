package com.ahrorovk.zikr.zikr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ZikrItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(16.dp)
            .padding(top = 40.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Blue else Color.White
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp, top = 20.dp),
            style = MaterialTheme.typography.titleLarge,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun SingleZikrItem() {
    var isSelected by remember { mutableStateOf(false) }

    ZikrItem(
        text = "My Zikr",
        isSelected = isSelected,
        onClick = { isSelected = !isSelected }
    )
}

