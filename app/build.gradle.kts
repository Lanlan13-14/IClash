plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") // Kotlinx Serialization 插件
}

android {
    namespace = "com.iclash"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.iclash"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Compose Compiler 版本
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Compose BOM 管理版本
    implementation(platform("androidx.compose:compose-bom:2025.06.00"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")

    // Kotlinx Serialization JSON 库
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // 其他你可能用到的库，比如 Ktor
    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
}
