package com.iclash.ui.dashboard

import kotlinx.serialization.json.*
import java.text.SimpleDateFormat
import java.util.*

fun parseSubscriptionInfo(json: String): String {
    return try {
        val root = Json.parseToJsonElement(json).jsonObject
        val name = root["name"]?.jsonPrimitive?.content ?: "未知配置"
        val userInfo = root["subscription-userinfo"]?.jsonPrimitive?.content ?: return "$name · 非订阅配置"

        val params = userInfo.split(";").associate {
            val (k, v) = it.trim().split("=")
            k to v.toLongOrNull()
        }

        val total = params["total"] ?: return "$name · 无流量信息"
        val used = (params["download"] ?: 0L) + (params["upload"] ?: 0L)
        val expire = params["expire"]?.let { formatTimestamp(it) } ?: "未知到期"

        val remaining = total - used
        "$name · ${formatBytes(remaining)} 剩余 · $expire"
    } catch (e: Exception) {
        "解析失败"
    }
}

fun formatBytes(bytes: Long): String {
    val kb = bytes / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0
    return when {
        gb >= 1 -> String.format("%.2f GB", gb)
        mb >= 1 -> String.format("%.2f MB", mb)
        else -> String.format("%.2f KB", kb)
    }
}

fun formatTimestamp(timestamp: Long): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.format(Date(timestamp * 1000))
    } catch (e: Exception) {
        "未知时间"
    }
}
