package com.iclash.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrafficCard(traffic: String) {
    Card(Modifier.fillMaxWidth().animateContentSize()) {
        Column(Modifier.padding(16.dp)) {
            Text("实时流量", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(traffic, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
