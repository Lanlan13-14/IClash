plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.iclash.android"
        minSdk = 24
        targetSdk = 33
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.ui)
    implementation(compose.material3)
}
