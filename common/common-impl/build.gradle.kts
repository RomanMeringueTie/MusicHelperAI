import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    id("com.autonomousapps.dependency-analysis")
    kotlin("plugin.serialization") version "2.3.21"
}

android {
    namespace = "com.example.music_helper.common.impl"
    compileSdk = 36
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }
    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    api(project(":common:common-api"))

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    api(libs.koin.core)
    implementation(libs.koin.android)
}
