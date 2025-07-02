package core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.descriptors.*

object MihomoController {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private const val BASE_URL = "http://127.0.0.1:9090"

    // region 首页功能

    suspend fun getConfigs(): String =
        client.get("$BASE_URL/configs").bodyAsText()

    suspend fun setMode(mode: String): String =
        client.patch("$BASE_URL/configs") {
            contentType(ContentType.Application.Json)
            setBody("""{"mode":"$mode"}""")
        }.bodyAsText()

    suspend fun getTraffic(): String {
        val res = client.get("$BASE_URL/traffic").body<TrafficResponse>()
        return "${res.up.formatSpeed()} ↑ / ${res.down.formatSpeed()} ↓"
    }

    // endregion

    // region 配置页

    suspend fun getProviders(): String =
        client.get("$BASE_URL/providers/proxies").bodyAsText()

    suspend fun updateProvider(name: String): String =
        client.put("$BASE_URL/providers/proxies/$name").bodyAsText()

    suspend fun getProxyProviders(): String =
        client.get("$BASE_URL/proxy-providers").bodyAsText()

    suspend fun getRuleProviders(): String =
        client.get("$BASE_URL/rule-providers").bodyAsText()

    // endregion

    // region 代理页

    suspend fun getProxies(): String =
        client.get("$BASE_URL/proxies").bodyAsText()

    suspend fun getDelay(name: String): Int =
        client.get("$BASE_URL/proxies/$name/delay") {
            parameter("timeout", 3000)
            parameter("url", "http://www.gstatic.com/generate_204")
        }.body<DelayResponse>().delay

    suspend fun switchProxy(group: String, target: String): String =
        client.put("$BASE_URL/proxies/$group") {
            contentType(ContentType.Application.Json)
            setBody("""{"name":"$target"}""")
        }.bodyAsText()

    // endregion

    // region 设置页

    suspend fun getVersion(): String =
        client.get("$BASE_URL/version").bodyAsText()

    suspend fun upgradeCore(): String =
        client.get("$BASE_URL/upgrade").bodyAsText()

    suspend fun upgradeGeo(): String =
        client.get("$BASE_URL/upgrade/geo").bodyAsText()

    suspend fun restart(): String =
        client.get("$BASE_URL/restart").bodyAsText()

    // endregion
}

// region 数据模型

@Serializable
data class TrafficResponse(val up: Long, val down: Long)

@Serializable
data class DelayResponse(val delay: Int)

// endregion

// region 工具函数

fun Long.formatSpeed(): String {
    val kb = this / 1024.0
    return if (kb < 1024) String.format("%.1f KB/s", kb)
    else String.format("%.1f MB/s", kb / 1024)
}

// endregion
