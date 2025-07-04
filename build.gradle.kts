plugins {
    // 与 Android 15 (API 35) 正式版配套的 AGP
    id("com.android.application") version "8.6.1" apply false
    // Kotlin 1.9.25 + 序列化编译插件
    kotlin("android")                     version "1.9.25" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.25" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
