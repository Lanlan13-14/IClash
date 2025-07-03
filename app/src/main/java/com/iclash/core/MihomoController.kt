package com.iclash.core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

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
}

@Serializable
data class TrafficResponse(val up: Long, val down: Long)

fun Long.formatSpeed(): String {
    val kb = this / 1024.0
    return if (kb < 1024) String.format("%.1f KB/s", kb)
    else String.format("%.1f MB/s", kb / 1024)
}
