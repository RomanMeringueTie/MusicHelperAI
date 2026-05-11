plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    id("com.google.gms.google-services")
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.music_helper"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.music_helper"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Feature modules
    implementation(project(":common:common-api"))
    implementation(project(":feature-auth:feature-auth-api"))
    implementation(project(":feature-listens:feature-listens-api"))
    implementation(project(":feature-apps:feature-apps-api"))
    implementation(project(":feature-permission:feature-permission-api"))
    implementation(project(":feature-analysis:feature-analysis-api"))
    implementation(project(":feature-stats:feature-stats-api"))
    implementation(project(":feature-settings:feature-settings-api"))
    implementation(project(":feature-onboarding:feature-onboarding-api"))
    // TODO(Remove impl dependencies)
    implementation(project(":common:common-impl"))
    implementation(project(":feature-auth:feature-auth-impl"))
    implementation(project(":feature-listens:feature-listens-impl"))
    implementation(project(":feature-apps:feature-apps-impl"))
    implementation(project(":feature-permission:feature-permission-impl"))
    implementation(project(":feature-analysis:feature-analysis-impl"))
    implementation(project(":feature-stats:feature-stats-impl"))
    implementation(project(":feature-settings:feature-settings-impl"))
    implementation(project(":feature-onboarding:feature-onboarding-impl"))

    // Firebase
    implementation(platform(libs.firebase.bom))

    // Firebase Auth
    implementation(libs.firebase.ui.auth)

    // Firebase AI
    implementation(libs.firebase.ai)

    // FireStore
    implementation(libs.firebase.firestore)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Kotlinx DateTime
    implementation(libs.kotlinx.datetime)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Immutable Collections
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.androidx.foundation)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
}