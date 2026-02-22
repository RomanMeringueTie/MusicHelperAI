plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.music_helper.feature.listens.api"
    compileSdk = 36
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    api(project(":common:common-api"))

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.ui.unit)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.sqlite)

    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.lifecycle.viewmodel)
}
