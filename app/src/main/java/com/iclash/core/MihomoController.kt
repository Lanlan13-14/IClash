package com.iclash.core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

object MihomoController {
    private val client = HttpClient {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }
    private const val BASE = "http://127.0.0.1:9090"

    suspend fun getConfigs(): String =
        client.get("$BASE/configs").bodyAsText()

    suspend fun setMode(mode: String): String =
        client.patch("$BASE/configs") {
            contentType(ContentType.Application.Json)
            setBody("""{"mode":"$mode"}""")
        }.bodyAsText()

    suspend fun getTraffic(): String {
        val js = client.get("$BASE/traffic").body<JsonElement>().jsonObject
        val up   = js["up"]?.jsonPrimitive?.long ?: 0L
        val down = js["down"]?.jsonPrimitive?.long ?: 0L
        return format(up) + " ↑ / " + format(down) + " ↓"
    }

    suspend fun getProviders(): List<String> =
        client.get("$BASE/providers/proxies").body<JsonArray>().map { it.jsonPrimitive.content }

    suspend fun updateProvider(name: String): Boolean =
        client.put("$BASE/providers/proxies/$name").bodyAsText().isNotBlank()

    suspend fun getProxies(): ProxiesResponse =
        client.get("$BASE/proxies").body()

    suspend fun getDelay(name: String): Int =
        client.get("$BASE/proxies/$name/delay") {
            parameter("timeout", 3000)
            parameter("url", "http://www.gstatic.com/generate_204")
        }.body<DelayResponse>().delay

    suspend fun switchProxy(group: String, target: String): String =
        client.put("$BASE/proxies/$group") {
            contentType(ContentType.Application.Json)
            setBody("""{"name":"$target"}""")
        }.bodyAsText()

    private fun format(b: Long): String {
        val kb = b / 1024.0
        return if (kb < 1024) "%.1f KB/s".format(kb) else "%.1f MB/s".format(kb / 1024)
    }
}

@Serializable
data class ProxiesResponse(val proxies: Map<String, List<ProxyItem>>)

@Serializable
data class ProxyItem(
    val name: String,
    val type: String? = null,
    val now: String? = null,
    val delay: Int? = null,
    val hidden: Boolean? = false
)

@Serializable
data class DelayResponse(val delay: Int)
