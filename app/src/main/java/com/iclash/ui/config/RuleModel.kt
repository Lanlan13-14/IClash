package com.iclash.ui.config

import kotlinx.serialization.Serializable

@Serializable
data class OverwriteRule(
    val type: String,
    val value: String,
    val target: String,
    val position: String, // prepend or append
    val note: String = ""
)
