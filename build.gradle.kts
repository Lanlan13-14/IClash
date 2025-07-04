plugins {
    id("com.android.application") version "8.2.0" apply false
    kotlin("android") version "1.9.10" apply false
}

// 不要再出现 allprojects { repositories { … } } 或 subprojects { … }