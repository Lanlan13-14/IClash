package com.iclash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.compose.*
import com.iclash.ui.dashboard.DashboardPage
import com.iclash.ui.config.ConfigPage
import com.iclash.ui.config.ProvidersPage
import com.iclash.ui.config.RuleOverwritePage
import com.iclash.ui.proxy.ProxyPage
import com.iclash.ui.settings.SettingsPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = "dashboard") {
                        composable("dashboard") { DashboardPage(nav) }
                        composable("config")    { ConfigPage(nav) }
                        composable("providers") { ProvidersPage() }
                        composable("overwrite") { RuleOverwritePage() }
                        composable("proxy")     { ProxyPage() }
                        composable("settings")  { SettingsPage() }
                    }
                }
            }
        }
    }
}
