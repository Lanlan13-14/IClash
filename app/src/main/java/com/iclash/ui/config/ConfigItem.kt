package com.iclash.ui.config

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfigItem(
    name: String,
    onOpenProviders: () -> Unit,
    onOpenOverwrite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row {
                Button(onClick = onOpenProviders) { Text("Providers") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = onOpenOverwrite) { Text("规则覆写") }
            }
        }
    }
}

