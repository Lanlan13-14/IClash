package com.iclash.ui.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun AddSubscriptionDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, url: String, ua: String) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var url by remember { mutableStateOf(TextFieldValue()) }
    var ua by remember { mutableStateOf("clash.meta") }
    val options = listOf("clash.meta", "clash", "mihomo")
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加订阅配置") },
        text = {
            Column {
                OutlinedTextField(name, { name = it }, label = { Text("名称") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(url, { url = it }, label = { Text("订阅链接") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Text("User-Agent")
                Box {
                    OutlinedTextField(
                        value = ua, onValueChange = {}, readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        options.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                ua = it; expanded = false
                            })
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name.text, url.text, ua) }) { Text("添加") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}
