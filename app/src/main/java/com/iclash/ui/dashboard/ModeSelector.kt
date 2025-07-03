package com.iclash.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModeSelector(mode: String, onModeChange: (String) -> Unit) {
    val options = listOf("rule", "global", "direct")
    Row {
        options.forEach {
            val selected = it == mode
            Button(
                onClick = { onModeChange(it) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(it.uppercase())
            }
        }
    }
}
