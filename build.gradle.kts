plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}

tasks.wrapper {
    gradleVersion = "7.4.2"
}

allprojects {
    group = extra["pGroup"] as String
    version = extra["pVersion"] as String
}

subprojects {
    repositories {
        maven("https://mvn.topobyte.de")
        maven("https://mvn.slimjars.com")
        mavenCentral()
        google()
    }
}
