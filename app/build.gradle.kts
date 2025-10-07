plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ahrorovk.tasbihfarzun"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ahrorovk.tasbihfarzun"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.compose.material3:material3:1.3.1")
    }
}

dependencies {
    // Project modules
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":model"))
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":common:components"))
    implementation(project(":features:prayertimes"))
    implementation(project(":features:zikr"))
    implementation(project(":features:settings"))

    // Android Core
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.material)
    implementation(libs.android.core.ktx)
    implementation(libs.android.datastore)
    implementation(libs.android.navigation.runtime)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
implementation(libs.compose.material)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.constraint)
    implementation(libs.compose.tooling.preview)
    implementation(libs.compose.icons)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.room.paging)

    // Paging
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    // Ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.android)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.websockets)
    implementation(libs.ktor.logging)
    implementation(libs.logback.classic)
    implementation(libs.kotlinx.serialization.json)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.test)

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.coil.svg)

    // Accompanist
    implementation(libs.accompanist.animation)
    implementation(libs.accompanist.flowrow)
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.permissions)
    // Other
    implementation(libs.android.prettytime)
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // Test
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit.ext)
    androidTestImplementation(libs.test.espresso)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.manifest)
}