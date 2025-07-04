package com.iclash.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModeSelector(selected: String, onSelect: (String) -> Unit) {
    val options = listOf("rule", "global", "direct")
    Row {
        options.forEach { opt ->
            val isSelected = opt == selected
            Button(
                onClick = { onSelect(opt) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(opt.uppercase())
            }
        }
    }
}
