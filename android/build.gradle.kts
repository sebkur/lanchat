import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
val haveKeystoreProperties = keystorePropertiesFile.exists()
if (haveKeystoreProperties) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

val rootVersionCode = version as String
val androidVersionCode = (extra["androidVersionCode"] as String).toInt()

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

android {
    compileSdk = 35
    namespace = "de.mobanisto.apps.lanchat"

    defaultConfig {
        minSdk = 21
        targetSdk = 35
        versionCode = androidVersionCode
        versionName = rootVersionCode
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    if (haveKeystoreProperties) {
        signingConfigs {
            create("release") {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (haveKeystoreProperties) {
                signingConfig = signingConfigs.getByName("release")
            }
            isDebuggable = false
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }
}

dependencies {
    implementation(project(":lanchat-core"))
    implementation(project(":lanchat-compose"))
    implementation(compose.material)
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-compose:1.10.0")
}

