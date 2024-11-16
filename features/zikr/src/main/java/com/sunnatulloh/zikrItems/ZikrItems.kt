import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardSelection()
        }
    }
}

@Composable
fun CardSelection() {
    val selectedCard = remember { mutableStateOf(-1) }
    val cards = listOf("Card 1", "Card 2", "Card 3")

    Row(modifier = Modifier.padding(top = 50.dp, start = 8.dp, end = 8.dp)) {
        cards.forEachIndexed { index, card ->
            Card(
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
                    .clickable { selectedCard.value = index },
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedCard.value == index) Color.Blue else Color.White,
                    contentColor = if (selectedCard.value == index) Color.White else Color.Black
                )
            ) {
                Text(
                    text = card,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge // Для Material3
                )
            }
        }
    }
}
