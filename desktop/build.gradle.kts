import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":lanchat-core"))
                implementation(project(":lanchat-compose"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "de.mobanisto.lanchat.LanChatKt"

        nativeDistributions {
            targetFormats(TargetFormat.Deb)
            packageName = "Lanchat"
            description = "Lanchat - Insecure Network Chat"
            vendor = "Mobanisto"
            copyright = "2022 Mobanisto"
            licenseFile.set(project.file("LICENSE.txt"))
            linux {
                packageName = "lanchat"
                debPackageVersion = "0.0.1"
                appCategory = "comm"
                menuGroup = "Network;Chat;InstantMessaging;"
            }
        }
    }
}

