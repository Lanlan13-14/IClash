package com.iclash.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iclash.core.MihomoController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DashboardPage(nav: NavController) {
    val scope = rememberCoroutineScope()
    var mode by remember { mutableStateOf("rule") }
    var traffic by remember { mutableStateOf("0 KB/s ↑ / 0 KB/s ↓") }
    var info by remember { mutableStateOf("加载中...") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        ModeSelector(selected = mode) {
            mode = it
            scope.launch { MihomoController.setMode(it) }
        }
        Spacer(Modifier.height(16.dp))
        SubscriptionCard(info = info)
        Spacer(Modifier.height(16.dp))
        TrafficCard(traffic = traffic)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { nav.navigate("config") }) { Text("配置管理") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { nav.navigate("proxy") })  { Text("代理管理") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { nav.navigate("settings") }) { Text("设置") }
    }

    LaunchedEffect(Unit) {
        while (true) {
            traffic = MihomoController.getTraffic()
            delay(2000)
        }
    }

    LaunchedEffect(Unit) {
        val js = MihomoController.getConfigs()
        info = parseSubscriptionInfo(js)
    }
}

