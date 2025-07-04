plugins {
    // Android 应用插件，暂时不在这里应用，只声明版本供子项目使用
    id("com.android.application") version "8.6.1" apply false
    // Kotlin Android 插件版本
    kotlin("android") version "1.9.25" apply false
    // Kotlin 序列化插件
    kotlin("plugin.serialization") version "1.9.25" apply false
}

// 根目录的 build.gradle.kts 一般不写 repositories 和 dependencies，
// 只负责声明插件版本和管理
