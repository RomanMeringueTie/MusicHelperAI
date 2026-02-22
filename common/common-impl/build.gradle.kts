plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.music_helper.common.impl"
    compileSdk = 36
    kotlinOptions {
        jvmTarget = "1.8"
    }
    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    api(project(":common:common-api"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    api(libs.koin.core)
}
