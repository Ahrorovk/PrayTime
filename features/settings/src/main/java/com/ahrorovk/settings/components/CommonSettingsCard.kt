package com.ahrorovk.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommonSettingsCard(
    icon: ImageVector,
    text: String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(shape = RoundedCornerShape(10.dp)),
        backgroundColor = Color(0xFF007FFD),
        border = BorderStroke(width = 10.dp, Color(0xFF007FFD))
    ) {
        Row(
           modifier = Modifier
               .fillMaxSize()
               .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Image(
                imageVector = icon,
                contentDescription = "Setting's icon"
            )
            Text(
                text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}