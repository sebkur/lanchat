pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://mvn.topobyte.de")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String
        val pinpitVersion = extra["pinpit.version"] as String
        val versionAccessPlugin = extra["version.access.version"] as String
        val ktlintVersion = extra["ktlint.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
        id("de.mobanisto.pinpit").version(pinpitVersion)
        id("de.topobyte.version-access-gradle-plugin").version(versionAccessPlugin)
        id("org.jlleitschuh.gradle.ktlint").version(ktlintVersion)
    }
}

include("core", "cli", "compose", "desktop", "android")
project(":core").name = "lanchat-core"
project(":cli").name = "lanchat-cli"
project(":compose").name = "lanchat-compose"
project(":desktop").name = "lanchat-desktop"
project(":android").name = "lanchat-android"
