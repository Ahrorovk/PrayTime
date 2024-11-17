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
fun ZikrItem() {
    var isSelected by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(top = 50.dp, start = 20.dp)
            .clickable { isSelected = !isSelected },
        colors = CardDefaults.cardColors(
            containerColor = if(isSelected) Color.Blue else Color.White
        ))
    {
        Text(
            text = "Zikr 1",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = if(isSelected) Color.White else Color.Black
        )
    }
}