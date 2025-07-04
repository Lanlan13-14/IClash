plugins {
    // Android Gradle Plugin (AGP)
    id("com.android.application") version "8.6.1" apply false
    // Kotlin + Kotlin序列化插件
    kotlin("android") version "1.9.25" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.25" apply false
}

// 去掉 allprojects 的 repositories 块
// allprojects {
//     repositories {
//         google()
//         mavenCentral()
//     }
// }
