import androidx.compose.material3.*
import androidx.compose.ui.window.*
import androidx.compose.runtime.*
import core.MihomoController
import kotlinx.coroutines.*

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "IClash") {
        MaterialTheme {
            var config by remember { mutableStateOf("加载中...") }

            LaunchedEffect(Unit) {
                config = MihomoController.getConfigs()
            }

            Text("配置: $config")
        }
    }
}
