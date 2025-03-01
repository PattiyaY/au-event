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
    testImplementation("org.robolectric:robolectric:4.10")
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)
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

    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation("com.google.android.gms:play-services-auth:20.4.1")
    implementation("io.coil-kt:coil-compose:2.2.2")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    testImplementation("androidx.test:core:1.4.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    testImplementation("io.mockk:mockk:1.10.5")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0-alpha04")

    testImplementation("io.mockk:mockk:1.13.5") // Latest stable version
    testImplementation("androidx.compose.ui:ui-test-junit4:1.6.0") // Ensure you have Compose UI testing

    // Unit Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("androidx.arch.core:core-testing:2.2.0") // ViewModel testing

    // Instrumentation Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")

    androidTestImplementation("io.mockk:mockk-android:1.13.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-inline:5.11.0") // For mocking final classes if needed

    testImplementation("io.ktor:ktor-server-test-host:2.3.7")
    testImplementation("io.ktor:ktor-client-cio:2.3.7")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

    implementation("com.onesignal:OneSignal:[5.0.0, 5.1.99]")

    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-location:21.1.0")

}