import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(project(":lanchat-core"))
                implementation("com.halilibo.compose-richtext:richtext-commonmark:0.16.0") {
                    exclude(group = "org.jetbrains.skiko", module = "skiko-awt-runtime-linux-x64")
                }
            }
        }
    }
}

android {
    compileSdk = 33
    namespace = "de.mobanisto.lanchat.common"

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}
