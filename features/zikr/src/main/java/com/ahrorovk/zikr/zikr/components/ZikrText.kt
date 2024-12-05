package com.ahrorovk.zikr.zikr.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BigBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(16.dp)
            .background(
                Color(0x8B007FFD),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Player",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Favourite",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}