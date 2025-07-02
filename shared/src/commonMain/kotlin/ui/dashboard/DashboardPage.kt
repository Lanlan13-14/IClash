package ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.MihomoController
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardPage() {
    val scope = rememberCoroutineScope()
    var mode by remember { mutableStateOf("rule") }
    var traffic by remember { mutableStateOf("0 KB/s ↑ / 0 KB/s ↓") }
    var subscriptionInfo by remember { mutableStateOf("加载中...") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 出站模式切换
        ModeSelector(mode = mode, onModeChange = {
            mode = it
            scope.launch { MihomoController.setMode(it) }
        })

        Spacer(Modifier.height(16.dp))

        // 订阅信息卡片
        SubscriptionCard(info = subscriptionInfo)

        Spacer(Modifier.height(16.dp))

        // 实时流量卡片
        TrafficCard(traffic = traffic)

        // 自动刷新流量
        LaunchedEffect(Unit) {
            while (true) {
                traffic = MihomoController.getTraffic()
                delay(2000)
            }
        }

        // 加载订阅信息
        LaunchedEffect(Unit) {
            val configJson = MihomoController.getConfigs()
            subscriptionInfo = parseSubscriptionInfo(configJson)
        }
    }
}

@Composable
fun ModeSelector(mode: String, onModeChange: (String) -> Unit) {
    val options = listOf("rule", "global", "direct")
    Row {
        options.forEach {
            val selected = it == mode
            Button(
                onClick = { onModeChange(it) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(it.uppercase())
            }
        }
    }
}

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

@Composable
fun TrafficCard(traffic: String) {
    Card(modifier = Modifier.fillMaxWidth().animateContentSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("实时流量", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(traffic, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// region 工具函数

fun parseSubscriptionInfo(json: String): String {
    return try {
        val root = Json.parseToJsonElement(json).jsonObject
        val name = root["name"]?.jsonPrimitive?.content ?: "未知配置"
        val userInfo = root["subscription-userinfo"]?.jsonPrimitive?.content ?: return "$name · 非订阅配置"

        val params = userInfo.split(";").associate {
            val (k, v) = it.trim().split("=")
            k to v.toLongOrNull()
        }

        val total = params["total"] ?: return "$name · 无流量信息"
        val used = (params["download"] ?: 0L) + (params["upload"] ?: 0L)
        val expire = params["expire"]?.let { formatTimestamp(it) } ?: "未知到期"

        val remaining = total - used
        "$name · ${formatBytes(remaining)} 剩余 · $expire"
    } catch (e: Exception) {
        "解析失败"
    }
}

fun formatBytes(bytes: Long): String {
    val kb = bytes / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0
    return when {
        gb >= 1 -> String.format("%.2f GB", gb)
        mb >= 1 -> String.format("%.2f MB", mb)
        else -> String.format("%.2f KB", kb)
    }
}

fun formatTimestamp(timestamp: Long): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.format(Date(timestamp * 1000))
    } catch (e: Exception) {
        "未知时间"
    }
}

// endregion
