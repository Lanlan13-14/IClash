package ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubscriptionCard(info: String) {
    Card(modifier = Modifier.fillMaxWidth().animateContentSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("当前订阅", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(info, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
