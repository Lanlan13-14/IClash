package com.iclash.ui.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iclash.core.MihomoController
import kotlinx.coroutines.launch

@Composable
fun ProvidersPage() {
    val scope = rememberCoroutineScope()
    var providers by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        providers = try { MihomoController.getProviders() } catch (_: Exception) { emptyList() }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Providers") },
            actions = {
                TextButton(onClick = { scope.launch { providers.forEach { MihomoController.updateProvider(it) } } }) {
                    Text("全部更新")
                }
            }
        )
    }) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(providers.size) { i ->
                val name = providers[i]
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(name)
                        Button(onClick = { scope.launch { MihomoController.updateProvider(name) } }) {
                            Text("更新")
                        }
                    }
                }
            }
        }
    }
}
