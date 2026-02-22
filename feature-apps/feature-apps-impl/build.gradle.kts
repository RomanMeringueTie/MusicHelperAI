plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.music_helper.feature.apps.impl"
    compileSdk = 36
    defaultConfig {
        minSdk = 21
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":common:common-api"))
    implementation(project(":feature-apps:feature-apps-api"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
}
