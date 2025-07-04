package com.iclash.ui.dashboard

import kotlinx.serialization.json.*
import java.text.SimpleDateFormat
import java.util.*

fun parseSubscriptionInfo(json: String): String {
    return try {
        val root = Json.parseToJsonElement(json).jsonObject
        val name = root["name"]?.jsonPrimitive?.content ?: "未知配置"
        val ui = root["subscription-userinfo"]?.jsonPrimitive?.content
        val pp = root["proxy-providers"]?.jsonArray
        when {
            ui != null -> formatInfo(ui)
            pp != null && pp.isNotEmpty() -> formatInfo(pp[0].jsonPrimitive.content)
            else -> "$name · 非订阅配置"
        }
    } catch (_: Exception) {
        "解析失败"
    }
}

private fun formatInfo(raw: String): String {
    val map = raw.split(";").mapNotNull {
        it.split("=").takeIf { parts -> parts.size == 2 }?.let { (k, v) -> k to v.toLongOrNull() }
    }.toMap()
    val total = map["total"] ?: return "无流量信息"
    val used = (map["download"] ?: 0L) + (map["upload"] ?: 0L)
    val rem = total - used
    val exp = map["expire"]?.let { Date(it * 1000).let { d -> SimpleDateFormat("yyyy-MM-dd").format(d) } }
        ?: "未知到期"
    return "${formatBytes(rem)} 剩余 · $exp"
}

private fun formatBytes(bytes: Long): String {
    val kb = bytes / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0
    return when {
        gb >= 1 -> "%.2f GB".format(gb)
        mb >= 1 -> "%.2f MB".format(mb)
        else -> "%.2f KB".format(kb)
    }
}
