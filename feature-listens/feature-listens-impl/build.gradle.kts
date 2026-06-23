plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    alias(libs.plugins.kotlin.android)
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.maps.feature.listens.impl"
    compileSdk = 36
    defaultConfig {
        minSdk = 21
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":feature-listens:feature-listens-api"))
    api(project(":feature-analysis:feature-analysis-api"))
    implementation(project(":common:common-api"))

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.koin.android)
    implementation(libs.koin.core.viewmodel)
    api(libs.koin.core)

    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.kotlinx.coroutines.play.services)
}
