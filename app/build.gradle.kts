plugins {
    kotlin("plugin.serialization") version "1.8.10"
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.auevent"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.auevent"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.storage.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Jetpack Compose
    implementation(libs.ui)
    implementation(libs.material3)

    // Coil for Image Loading
    implementation(libs.coil.compose)

    // Google Sign-In
    implementation (libs.play.services.auth)

    implementation(libs.coil.compose.v222)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.material.icons.extended)

    implementation(libs.androidx.material.icons.extended.v151)

    // Glide(dispaly image by link)
    implementation(libs.glide)
    implementation(libs.retrofit)

    // Firebase Storage
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation("io.ktor:ktor-client-core:2.3.2")
    implementation("io.ktor:ktor-client-android:2.3.2")
    implementation("io.ktor:ktor-client-logging:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}