plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.iclash"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.iclash"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    sourceSets {
        getByName("main").res.srcDirs("src/main/res")
    }

    // 1. 确保 Java 源码与字节码都编译到 Java 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // 2. Kotlin 编译选项（保留 jvmTarget = "17" 也没问题）
    kotlinOptions {
        jvmTarget = "17"
    }
}

// 3. 给 Kotlin 指定统一的 JVM toolchain（Java 17）
kotlin {
    jvmToolchain(17)
}

// 4. （可选）统一所有 KotlinCompile 任务的 jvmTarget
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // AndroidX & Compose
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Ktor & Serialization
    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // AppCompat & MaterialComponents（提供 XML 主题与 attrs）
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
}