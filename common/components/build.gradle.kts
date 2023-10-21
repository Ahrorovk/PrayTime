plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.ORG_KOTLIN_ANDROID)
    id(GradlePlugin.KAPT)
    id(GradlePlugin.DAGGER_HILT)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    // Android
    implementation(Dependencies.android.lifecycleRuntime)
    implementation(Dependencies.android.navigationRuntime)
    implementation(Dependencies.android.dataStore)
    implementation(Dependencies.android.lifecycleViewmodel)
    implementation(Dependencies.android.ktx)
    implementation(Dependencies.android.material)
    // Compose
    implementation(Dependencies.compose.icons)
    implementation(Dependencies.compose.material)
    implementation(Dependencies.compose.activity)
    implementation(Dependencies.compose.navigation)
    implementation(Dependencies.compose.viewModel)
    implementation(Dependencies.compose.constraintLayout)
    implementation(Dependencies.compose.uiToolingPreview)
    // implementation(Dependencies.compose.ui)
    //implementation(Dependencies.compose.uiTest)
    // Hilt
    implementation(Dependencies.android.hilt.android)
    kapt(Dependencies.android.hilt.androidCompiler)
    kapt(Dependencies.android.hilt.compiler)
    implementation(Dependencies.android.hilt.navigation)
    //Coroutines
    implementation(Dependencies.coroutines.android)
    implementation(Dependencies.coroutines.test)
    implementation(Dependencies.coroutines.core)
    // Paging
    implementation(Dependencies.paging.compose)
    implementation(Dependencies.paging.runtime)
    // Accompanist
    implementation(Dependencies.accompanist.animation)
    implementation(Dependencies.accompanist.flowRow)
    implementation(Dependencies.accompanist.systemUiController)
    // Pretty time
    implementation(Dependencies.android.prettyTime)
    implementation(Dependencies.android.dataStore)
    // DatePicker
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")
}