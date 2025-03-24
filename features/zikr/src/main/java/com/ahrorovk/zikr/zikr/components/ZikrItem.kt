package com.ahrorovk.zikr.zikr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ZikrItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .size(180.dp)
            .padding(30.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if(isSelected) Color(0xFF007FFD) else Color(0xFFFFFFFF)
        )
    ){
        Text(
            text = text,
            fontSize = 20.sp,
            color = if(isSelected) Color.White else Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}

