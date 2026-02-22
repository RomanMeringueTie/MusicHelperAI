plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.music_helper.feature.settings.api"
    compileSdk = 36
    defaultConfig {
        minSdk = 23
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
    implementation(project(":common:common-api"))
    implementation(project(":feature-permission:feature-permission-api"))
    implementation(project(":feature-apps:feature-apps-api"))
    implementation(project(":feature-auth:feature-auth-api"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}
