plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ahrorovk.prayertimes"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Project modules
    implementation(project(":data"))
    implementation(project(":common:components"))
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":model"))

    // Android Core
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.datastore)
    implementation(libs.android.navigation.runtime)
    implementation(libs.android.core.ktx)
    implementation(libs.android.material)
    implementation("androidx.work:work-runtime-ktx:2.9.1")

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

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.test)
    implementation(libs.coroutines.core)

    // Paging
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)

    // Accompanist
    implementation(libs.accompanist.animation)
    implementation(libs.accompanist.flowrow)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.systemui)
    implementation("com.google.accompanist:accompanist-pager:0.25.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.25.1")

    // Other
    implementation(libs.android.prettytime)

    // Sheets Compose Dialogs
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

    // Test
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit.ext)
    androidTestImplementation(libs.test.espresso)
}