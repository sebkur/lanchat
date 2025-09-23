import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("de.mobanisto.pinpit")
    id("org.jlleitschuh.gradle.ktlint")
}

val attributeUsage = Attribute.of("org.gradle.usage", String::class.java)

val currentOs: Configuration by configurations.creating {
    extendsFrom(configurations.implementation.get())
    attributes { attribute(attributeUsage, "java-runtime") }
}

val windowsX64: Configuration by configurations.creating {
    extendsFrom(configurations.implementation.get())
    attributes { attribute(attributeUsage, "java-runtime") }
}

val linuxX64: Configuration by configurations.creating {
    extendsFrom(configurations.implementation.get())
    attributes { attribute(attributeUsage, "java-runtime") }
}

val linuxArm64: Configuration by configurations.creating {
    extendsFrom(configurations.implementation.get())
    attributes { attribute(attributeUsage, "java-runtime") }
}

val macosX64: Configuration by configurations.creating {
    extendsFrom(configurations.implementation.get())
    attributes { attribute(attributeUsage, "java-runtime") }
}

val macosArm64: Configuration by configurations.creating {
    extendsFrom(configurations.implementation.get())
    attributes { attribute(attributeUsage, "java-runtime") }
}

sourceSets {
    main {
        java {
            compileClasspath = currentOs
            runtimeClasspath = currentOs
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation(project(":lanchat-core"))
    implementation(project(":lanchat-compose"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    currentOs(compose.desktop.currentOs)
    windowsX64(compose.desktop.windows_x64)
    linuxX64(compose.desktop.linux_x64)
    linuxArm64(compose.desktop.linux_arm64)
    macosX64(compose.desktop.macos_x64)
    macosArm64(compose.desktop.macos_arm64)
    implementation("com.github.ajalt.clikt:clikt:4.2.0")
    implementation("de.topobyte:shared-preferences:0.1.0")
}

val versionCode = version as String

pinpit.desktop {
    application {
        mainClass = "de.mobanisto.lanchat.LanChatKt"

        nativeDistributions {
            jvmVendor = "adoptium"
            jvmVersion = "17.0.8.1+1"
            modules("jdk.management")

            packageName = "Lanchat"
            packageVersion = versionCode
            description = "Lanchat - Insecure Network Chat"
            vendor = "Mobanisto"
            copyright = "2022-2023 Mobanisto"
            licenseFile.set(project.file("src/main/packaging/LICENSE.txt"))
            linux {
                packageName = "lanchat"
                debMaintainer = "sebastian@mobanisto.de"
                debPackageVersion = versionCode
                appCategory = "comm"
                menuGroup = "Network;Chat;InstantMessaging"
                iconFile.set(project.file("src/main/packaging/deb/lanchat.png"))
                debPreInst.set(project.file("src/main/packaging/deb/preinst"))
                debPostInst.set(project.file("src/main/packaging/deb/postinst"))
                debPreRm.set(project.file("src/main/packaging/deb/prerm"))
                debCopyright.set(project.file("src/main/packaging/deb/copyright"))
                debLauncher.set(project.file("src/main/packaging/deb/launcher.desktop"))
                deb("universalX64") {
                    qualifier = "universal"
                    arch = "x64"
                    depends(
                        "libc6", "libexpat1", "libgcc-s1", "libuuid1", "xdg-utils",
                        "zlib1g", "libnotify4"
                    )
                }
                distributableArchive {
                    format = "tar.gz"
                    arch = "x64"
                }
                distributableArchive {
                    format = "tar.gz"
                    arch = "arm64"
                }
                appImage {
                    arch = "x64"
                }
                appImage {
                    arch = "arm64"
                }
            }
            windows {
                dirChooser = true
                shortcut = true
                menuGroup = "Mobanisto"
                upgradeUuid = "CB418F88-237B-45A0-93DD-6D158443A020"
                packageVersion = versionCode
                iconFile.set(project.file("src/main/packaging/windows/lanchat.ico"))
                aumid = "Mobanisto.Lanchat"
                msi {
                    arch = "x64"
                    bitmapBanner.set(project.file("src/main/packaging/windows/banner.bmp"))
                    bitmapDialog.set(project.file("src/main/packaging/windows/dialog.bmp"))
                }
                distributableArchive {
                    format = "zip"
                    arch = "x64"
                }
            }
            macOS {
                packageName = "Lanchat"
                iconFile.set(project.file("../artwork/lanchat.icns"))
                bundleID = "de.mobanisto.lanchat"
                appCategory = "public.app-category.social-networking"
                distributableArchive {
                    format = "zip"
                    arch = "x64"
                }
                distributableArchive {
                    format = "zip"
                    arch = "arm64"
                }
            }
        }
    }
}
