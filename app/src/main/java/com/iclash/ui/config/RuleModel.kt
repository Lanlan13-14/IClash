package com.iclash.ui.config

data class OverwriteRule(
    val type: String,
    val value: String,
    val target: String,
    val position: String, // prepend or append
    val note: String = ""
)
