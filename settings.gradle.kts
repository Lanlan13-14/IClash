pluginManagement {
    repositories {
        // 只在 settings 里声明插件仓库
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // 禁止在各模块单独声明 repositories
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "IClash"
include(":app")