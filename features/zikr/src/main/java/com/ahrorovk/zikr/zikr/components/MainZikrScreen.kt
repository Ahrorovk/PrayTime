package com.ahrorovk.zikr.zikr.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ahrorovk.zikr.R

@Composable
fun ZikrScreen() {
    var isSelected by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }
    var progress by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painterResource(id = R.drawable.background2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        ZikrItem(
            text = "Zikr 1",
            isSelected = isSelected,
            onClick = { isSelected = !isSelected }
        )
        BigBox(text = "Text of 1 Zikr")
        Spacer(modifier = Modifier.height(10.dp))
        CounterCard(
            count = count,
            progress = progress,
            onClick = {
                count++
                progress = (count % 34) / 34f
            },
            onReset = {
                count = 0
            }
        )
    }
}