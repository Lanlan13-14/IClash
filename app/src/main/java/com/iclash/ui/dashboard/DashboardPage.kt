package com.iclash.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iclash.core.MihomoController
import kotlinx.coroutines.*

@Composable
fun DashboardPage() {
    val scope = rememberCoroutineScope()
    var mode by remember { mutableStateOf("rule") }
    var traffic by remember { mutableStateOf("0 KB/s ↑ / 0 KB/s ↓") }
    var subscriptionInfo by remember { mutableStateOf("加载中...") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ModeSelector(mode = mode, onModeChange = {
            mode = it
            scope.launch { MihomoController.setMode(it) }
        })

        Spacer(Modifier.height(16.dp))

        SubscriptionCard(info = subscriptionInfo)

        Spacer(Modifier.height(16.dp))

        TrafficCard(traffic = traffic)

        LaunchedEffect(Unit) {
            while (true) {
                traffic = MihomoController.getTraffic()
                delay(2000)
            }
        }

        LaunchedEffect(Unit) {
            val configJson = MihomoController.getConfigs()
            subscriptionInfo = parseSubscriptionInfo(configJson)
        }
    }
}
