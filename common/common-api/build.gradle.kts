plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    id("com.autonomousapps.dependency-analysis")
}

android {
    namespace = "com.example.music_helper.common.api"
    compileSdk = 36
    android.buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = 21
        signingConfig = signingConfigs.getByName("debug")
        buildConfigField("boolean", "IS_DEBUG", "false")
    }
    buildTypes {
        debug {
            buildConfigField("boolean", "IS_DEBUG", "true")
        }
        release {
            buildConfigField("boolean", "IS_DEBUG", "false")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(libs.androidx.ui.text)

    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.unit)
    implementation(libs.androidx.ui)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.annotation)

    implementation(libs.kotlinx.coroutines.core)
}
