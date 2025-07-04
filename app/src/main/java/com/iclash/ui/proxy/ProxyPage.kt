package com.iclash.ui.proxy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iclash.core.MihomoController
import com.iclash.core.ProxyItem
import com.iclash.core.ProxiesResponse
import kotlinx.coroutines.launch

@Composable
fun ProxyPage() {
    val scope = rememberCoroutineScope()
    var groups by remember { mutableStateOf<Map<String, List<ProxyItem>>>(emptyMap()) }
    var expanded by remember { mutableStateOf(setOf<String>()) }
    var selectedGroup by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var delays by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var doubleRow by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val res: ProxiesResponse = MihomoController.getProxies()
        // 过滤 hidden=true
        groups = res.proxies.mapValues { it.value.filter { item -> item.hidden != true } }
        selectedGroup = groups.mapValues { (_, list) ->
            list.find { it.now == it.name }?.name ?: list.firstOrNull()?.name.orEmpty()
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("代理管理") },
            actions = {
                TextButton(onClick = { doubleRow = !doubleRow }) {
                    Text(if (doubleRow) "单行" else "双行")
                }
            }
        )
    }) { padding ->
        LazyColumn(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            groups.forEach { (group, proxies) ->
                // 跳过无节点的组
                if (proxies.isEmpty()) return@forEach

                item {
                    val now = selectedGroup[group] ?: "—"
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                expanded = if (expanded.contains(group))
                                    expanded - group else expanded + group
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$group (当前: $now)", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = if (expanded.contains(group)) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null
                        )
                    }
                }

                if (expanded.contains(group)) {
                    item {
                        if (doubleRow) {
                            FlowRow(
                                Modifier.padding(horizontal = 16.dp),
                                mainAxisSpacing = 8.dp,
                                crossAxisSpacing = 8.dp
                            ) {
                                proxies.forEach { ProxyButton(it, group, selectedGroup, delays, scope) }
                            }
                        } else {
                            LazyRow(
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                proxies.forEach { proxy ->
                                    item { ProxyButton(proxy, group, selectedGroup, delays, scope) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProxyButton(
    proxy: ProxyItem,
    group: String,
    selectedGroup: Map<String, String>,
    delays: Map<String, Int>,
    scope: kotlinx.coroutines.CoroutineScope
) {
    val isSelected = selectedGroup[group] == proxy.name
    Button(
        onClick = {
            scope.launch {
                MihomoController.switchProxy(group, proxy.name)
                val d = MihomoController.getDelay(proxy.name)
                delays + (proxy.name to d)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.height(64.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(proxy.name, maxLines = 1)
            proxy.type?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            proxy.now?.let { Text("当前: $it", style = MaterialTheme.typography.bodySmall) }
            delays[proxy.name]?.let { Text("${it}ms", style = MaterialTheme.typography.bodySmall) }
        }
    }
}
