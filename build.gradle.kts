plugins {
    kotlin("multiplatform") version "1.9.10" apply false
    id("org.jetbrains.compose") version "1.5.10" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
