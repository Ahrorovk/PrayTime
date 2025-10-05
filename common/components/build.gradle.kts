plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ahrorovk.components"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
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
    implementation(project(":core"))

    // Android Core
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.datastore)
    implementation(libs.android.navigation.runtime)
    implementation(libs.android.core.ktx)
    implementation(libs.android.material)

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
    implementation(libs.accompanist.systemui)

    // Other
    implementation(libs.android.prettytime)

    // Sheets Compose Dialogs
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:state:1.0.2")

    // Test
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit.ext)
    androidTestImplementation(libs.test.espresso)
}
