plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.compose")
    id("de.mobanisto.pinpit")
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

sourceSets {
    main {
        java {
            compileClasspath = currentOs
            runtimeClasspath = currentOs
        }
    }
}

dependencies {
    implementation(project(":lanchat-core"))
    implementation(project(":lanchat-compose"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    currentOs(compose.desktop.currentOs)
    windowsX64(compose.desktop.windows_x64)
    linuxX64(compose.desktop.linux_x64)
}

val versionCode by extra("1.0.0")

pinpit.desktop {
    application {
        mainClass = "de.mobanisto.lanchat.LanChatKt"

        nativeDistributions {
            jvmVendor = "adoptium"
            jvmVersion = "17.0.5+8"

            packageName = "Lanchat"
            description = "Lanchat - Insecure Network Chat"
            vendor = "Mobanisto"
            copyright = "2022 Mobanisto"
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
                deb("UbuntuFocalX64") {
                    qualifier = "ubuntu-20.04"
                    arch = "x64"
                    depends(
                        "libc6", "libexpat1", "libgcc-s1", "libpcre3", "libuuid1", "xdg-utils",
                        "zlib1g", "libnotify4"
                    )
                }
            }
            windows {
                dirChooser = true
                perUserInstall = true
                shortcut = true
                menu = true
                menuGroup = "pinpit"
                upgradeUuid = "CB418F88-237B-45A0-93DD-6D158443A020"
                packageVersion = versionCode
                iconFile.set(project.file("src/main/packaging/windows/lanchat.ico"))
                msi {
                    arch = "x64"
                    bitmapBanner.set(project.file("src/main/packaging/windows/banner.bmp"))
                    bitmapDialog.set(project.file("src/main/packaging/windows/dialog.bmp"))
                }
            }
        }
    }
}

