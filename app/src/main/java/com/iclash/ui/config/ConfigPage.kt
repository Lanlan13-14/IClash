package com.iclash.ui.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iclash.core.MihomoController
import kotlinx.coroutines.launch

@Composable
fun ConfigPage(navController: NavController) {
    val scope = rememberCoroutineScope()
    var showAdd by remember { mutableStateOf(false) }
    var configs by remember { mutableStateOf(listOf<String>("默认配置")) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("配置管理") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAdd = true }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(configs.size) { i ->
                ConfigItem(
                    name = configs[i],
                    onOpenProviders = { navController.navigate("providers") },
                    onOpenOverwrite = { navController.navigate("overwrite") }
                )
            }
        }
        if (showAdd) {
            AddSubscriptionDialog(
                onDismiss = { showAdd = false },
                onConfirm = { name, url, ua ->
                    scope.launch {
                        configs = configs + name
                        showAdd = false
                    }
                }
            )
        }
    }
}
