package com.iclash.ui.settings

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun SettingsPage() {
    var showSubStore by remember { mutableStateOf(false) }
    var themeIdx by remember { mutableStateOf(0) }       // 0=系统,1=浅,2=深
    var langIdx by remember { mutableStateOf(0) }        // 0=中文,1=English
    var vpnRouting by remember { mutableStateOf(false) }
    var dnsHijack by remember { mutableStateOf(false) }
    var ipv6 by remember { mutableStateOf(false) }
    var systemProxy by remember { mutableStateOf(false) }
    var accessControl by remember { mutableStateOf(false) }
    var notifyService by remember { mutableStateOf(true) }
    var notifyTraffic by remember { mutableStateOf(true) }
    var appLock by remember { mutableStateOf(false) }
    var autoLock by remember { mutableStateOf(false) }
    var hideIcon by remember { mutableStateOf(false) }
    var overrideDns by remember { mutableStateOf(0) }
    var overridePort by remember { mutableStateOf(0) }
    var overrideController by remember { mutableStateOf(0) }
    var overrideGeo by remember { mutableStateOf(0) }

    val themeOptions = listOf("跟随系统", "浅色", "深色")
    val langOptions = listOf("中文", "English")
    val overrideOptions = listOf("不修改", "启用", "禁用")
    val uriHandler = LocalUriHandler.current

    Scaffold(topBar = {
        TopAppBar(title = { Text("设置") })
    }) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            ) {
                // Sub-Store
                item {
                    ListItem(
                        headlineText = { Text("Sub-Store") },
                        modifier = Modifier.clickable { showSubStore = true }
                    )
                }
                // 主题
                item {
                    ListItem(
                        headlineText = { Text("主题") },
                        supportingText = { Text(themeOptions[themeIdx]) },
                        modifier = Modifier.clickable {
                            themeIdx = (themeIdx + 1) % themeOptions.size
                        }
                    )
                }
                // 语言
                item {
                    ListItem(
                        headlineText = { Text("语言") },
                        supportingText = { Text(langOptions[langIdx]) },
                        modifier = Modifier.clickable {
                            langIdx = (langIdx + 1) % langOptions.size
                        }
                    )
                }
                // 网络设置
                item {
                    Text(
                        "网络设置",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item { SwitchListItem("VPN 路由", vpnRouting) { vpnRouting = it } }
                item { SwitchListItem("DNS 劫持", dnsHijack) { dnsHijack = it } }
                item { SwitchListItem("IPv6", ipv6) { ipv6 = it } }
                item { SwitchListItem("系统代理", systemProxy) { systemProxy = it } }
                item { SwitchListItem("访问控制", accessControl) { accessControl = it } }

                // 通知设置
                item {
                    Text(
                        "通知设置",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item { SwitchListItem("服务通知", notifyService) { notifyService = it } }
                item { SwitchListItem("流量显示", notifyTraffic) { notifyTraffic = it } }

                // 安全与隐私
                item {
                    Text(
                        "安全与隐私",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item { SwitchListItem("应用锁", appLock) { appLock = it } }
                item { SwitchListItem("自动锁定", autoLock) { autoLock = it } }
                item { SwitchListItem("隐藏图标", hideIcon) { hideIcon = it } }

                // 覆写设置
                item {
                    Text(
                        "覆写设置",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    DropdownListItem(
                        label = "覆写 DNS",
                        options = overrideOptions,
                        selectedIndex = overrideDns
                    ) { overrideDns = it }
                }
                item {
                    DropdownListItem(
                        label = "覆写 端口",
                        options = overrideOptions,
                        selectedIndex = overridePort
                    ) { overridePort = it }
                }
                item {
                    DropdownListItem(
                        label = "覆写 控制器",
                        options = overrideOptions,
                        selectedIndex = overrideController
                    ) { overrideController = it }
                }
                item {
                    DropdownListItem(
                        label = "覆写 Geo 数据",
                        options = overrideOptions,
                        selectedIndex = overrideGeo
                    ) { overrideGeo = it }
                }

                // 内核管理
                item {
                    Text(
                        "内核管理",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("升级内核") },
                        modifier = Modifier.clickable { /* TODO: 升级内核 */ }
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("升级 Geo 数据") },
                        modifier = Modifier.clickable { /* TODO: 升级 Geo */ }
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("重启服务") },
                        modifier = Modifier.clickable { /* TODO: 重启服务 */ }
                    )
                }

                // 备份与还原
                item {
                    Text(
                        "备份与还原",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("WebDAV 备份") },
                        modifier = Modifier.clickable { /* TODO: 打开 WebDAV 设置 */ }
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("恢复备份") },
                        modifier = Modifier.clickable { /* TODO: 恢复备份 */ }
                    )
                }

                // 关于
                item {
                    Text(
                        "关于",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("版本") },
                        supportingText = { Text("1.0.0") }
                    )
                }
                item {
                    ListItem(
                        headlineText = { Text("GitHub") },
                        modifier = Modifier.clickable {
                            uriHandler.openUri("https://github.com/your-org/IClash")
                        }
                    )
                }
            }

            if (showSubStore) {
                SubStoreWebView(onClose = { showSubStore = false })
            }
        }
    }
}

@Composable
private fun SwitchListItem(
    label: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onToggle(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}

@Composable
private fun DropdownListItem(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, Modifier.weight(1f))
        Text(options[selectedIndex])
    }
    if (expanded) {
        AlertDialog(
            onDismissRequest = { expanded = false },
            title = { Text(label) },
            text = {
                Column {
                    options.forEachIndexed { idx, option ->
                        Text(
                            option,
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(idx)
                                    expanded = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { expanded = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun SubStoreWebView(onClose: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        AndroidView(factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl("https://sub-store.vercel.app/")
            }
        })
        FloatingActionButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("×")
        }
    }
}
