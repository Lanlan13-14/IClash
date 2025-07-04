package com.iclash.ui.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iclash.core.RuleInjector
import kotlinx.coroutines.launch

@Composable
fun RuleOverwritePage() {
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }

    var enabled by remember { mutableStateOf(true) }
    var showAdd by remember { mutableStateOf(false) }
    var rules by remember { mutableStateOf(listOf<OverwriteRule>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("规则覆写") },
                actions = {
                    IconButton(onClick = { showAdd = true }) { Text("+") }
                    IconButton(onClick = {
                        scope.launch {
                            val ok = RuleInjector.inject(enabled, rules)
                            snackbarState.showSnackbar(if (ok) "注入成功" else "注入失败")
                        }
                    }) { Text("保存") }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row {
                Text("启用规则覆写")
                Spacer(Modifier.width(8.dp))
                Switch(enabled, onCheckedChange = { enabled = it })
            }
            Spacer(Modifier.height(16.dp))
            if (rules.isEmpty()) {
                Text("暂无规则，点击 + 添加")
            } else {
                LazyColumn {
                    items(rules.size) { i ->
                        val r = rules[i]
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("${r.type}, ${r.value}, ${r.target}")
                                if (r.note.isNotBlank()) {
                                    Text("备注：${r.note}", style = MaterialTheme.typography.bodySmall)
                                }
                                Text("位置：${if (r.position == "prepend") "最前面" else "最后面"}")
                            }
                        }
                    }
                }
            }
        }
        if (showAdd) {
            AddRuleDialog(
                onDismiss = { showAdd = false },
                onConfirm = {
                    rules = rules + it
                    showAdd = false
                }
            )
        }
    }
}

@Composable
fun AddRuleDialog(
    onDismiss: () -> Unit,
    onConfirm: (OverwriteRule) -> Unit
) {
    val types = listOf(
        "DOMAIN", "DOMAIN-SUFFIX", "DOMAIN-KEYWORD",
        "IP-CIDR", "IP-CIDR6", "GEOIP",
        "PROCESS-NAME", "DST-PORT", "SRC-PORT"
    )
    val targets = listOf("DIRECT", "REJECT", "ProxyGroupA", "ProxyGroupB")
    val positions = listOf("prepend" to "最前面", "append" to "最后面")

    var type by remember { mutableStateOf(types.first()) }
    var value by remember { mutableStateOf("") }
    var target by remember { mutableStateOf(targets.first()) }
    var posLabel by remember { mutableStateOf(positions.first().second) }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加规则") },
        text = {
            Column {
                DropdownField("规则类型", type, types) { type = it }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value, { value = it }, label = { Text("匹配内容") })
                Spacer(Modifier.height(8.dp))
                DropdownField("目标策略", target, targets) { target = it }
                Spacer(Modifier.height(8.dp))
                DropdownField("插入位置", posLabel, positions.map { it.second }) {
                    posLabel = it
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(note, { note = it }, label = { Text("备注（可选）") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val pos = positions.first { it.second == posLabel }.first
                onConfirm(OverwriteRule(type, value, target, pos, note))
            }) { Text("添加") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
fun DropdownField(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall)
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelected(it); expanded = false
                })
            }
        }
    }
}
