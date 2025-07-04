plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.9.10" // 这里用你的Kotlin版本
}

android {
    namespace = "com.iclash"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.iclash"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")

    // Kotlinx Serialization JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // AndroidX Core
    implementation("androidx.core:core-ktx:1.12.0")

    // Compose BOM 管理版本
    implementation(platform("androidx.compose:compose-bom:2025.06.00"))

    // Compose Material 3
    implementation("androidx.compose.material3:material3")

    // Compose UI
    implementation("androidx.compose.ui:ui")

    // Compose Activity
    implementation("androidx.activity:activity-compose:1.8.2")

    // Compose Material Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Compose Animation (动画支持)
    implementation("androidx.compose.animation:animation")

    // Testing (如果有需要)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.6")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}

