plugins {
    id("com.android.application")
    kotlin("android")
    // 与上层保持一致
    kotlin("plugin.serialization")
}

android {
    namespace  = "com.iclash"
    compileSdk = 35         // Android 15 正式版
    defaultConfig {
        applicationId = "com.iclash"
        minSdk        = 24
        targetSdk     = 35  // Google Play 2025‑08‑31 起必须 ≥35:contentReference[oaicite:1]{index=1}
        versionCode   = 1
        versionName   = "1.0.0"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"   // 对应 Kotlin 1.9.25:contentReference[oaicite:2]{index=2}
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    // --- Compose ---------------------------------------------------------
    implementation(platform("androidx.compose:compose-bom:2025.06.00")) :contentReference[oaicite:3]{index=3}
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // --- 基础 Android & Material -----------------------------------------
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    // --- 网络 / 序列化 ----------------------------------------------------
    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
