plugins {
    // Android Gradle Plugin
    id("com.android.application") version "8.5.0" apply false

    // Kotlin Android 插件，升级到与 Compose Compiler 兼容的版本
    kotlin("android") version "1.9.25" apply false
}