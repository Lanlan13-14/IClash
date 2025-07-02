package core

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*

object MihomoController {
    private val client = HttpClient()

    suspend fun getConfigs(): String =
        client.get("http://127.0.0.1:9090/configs").bodyAsText()

    suspend fun getProxies(): String =
        client.get("http://127.0.0.1:9090/proxies").bodyAsText()

    suspend fun getProviders(): String =
        client.get("http://127.0.0.1:9090/providers/proxies").bodyAsText()

    suspend fun getVersion(): String =
        client.get("http://127.0.0.1:9090/version").bodyAsText()

    suspend fun upgradeCore(): String =
        client.get("http://127.0.0.1:9090/upgrade").bodyAsText()
}
