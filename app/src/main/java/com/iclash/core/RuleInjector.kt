package com.iclash.core

import com.iclash.ui.config.OverwriteRule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

object RuleInjector {
    private val client = HttpClient {
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    }
    private const val BASE = "http://127.0.0.1:9090"

    suspend fun inject(overwriteEnabled: Boolean, rules: List<OverwriteRule>): Boolean {
        if (!overwriteEnabled || rules.isEmpty()) return false
        val text = client.get("$BASE/configs").bodyAsText()
        val cfg  = Json.parseToJsonElement(text).jsonObject
        val exist = cfg["rules"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList()
        val (pre, post) = rules.partition { it.position == "prepend" }
        val merged = pre.map { it.toClash() } + exist + post.map { it.toClash() }
        val body = buildJsonObject { put("rules", JsonArray(merged.map { JsonPrimitive(it) })) }
        val resp = client.patch("$BASE/configs") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return resp.status.value in 200..299
    }

    private fun OverwriteRule.toClash() = listOf(type, value, target).joinToString(",")
}
