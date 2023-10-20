plugins {
    id(GradlePlugin.ANDROID_APPLICATION)
    id(GradlePlugin.KOTLIN_ANDROID)
    id(GradlePlugin.KAPT)
    id(GradlePlugin.DAGGER_HILT)
}

android {
    namespace = "com.ahrorovk.praytime"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ahrorovk.praytime"
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
// Android
    implementation(Dependencies.android.lifecycleRuntime)
    implementation(Dependencies.android.navigationRuntime)
    implementation(Dependencies.android.dataStore)
    implementation(Dependencies.android.lifecycleViewmodel)
    implementation(Dependencies.android.ktx)
    implementation(Dependencies.android.material)
    //Coroutines
    implementation(Dependencies.coroutines.android)
    implementation(Dependencies.coroutines.core)
    implementation(Dependencies.coroutines.test)
    // Hilt
    implementation(Dependencies.android.hilt.android)
    kapt(Dependencies.android.hilt.androidCompiler)
    kapt(Dependencies.android.hilt.compiler)
    implementation(Dependencies.android.hilt.navigation)
    // Room
    implementation(Dependencies.android.room.ktx)
    implementation(Dependencies.android.room.runtime)
    kapt(Dependencies.android.room.compiler)
    implementation(Dependencies.android.room.paging)
    // Paging
    implementation(Dependencies.paging.compose)
    implementation(Dependencies.paging.runtime)
    // Retrofit
    implementation(Dependencies.network.retrofit.base)
    implementation(Dependencies.network.retrofit.gsonConverter)
    implementation(Dependencies.network.okHttp.base)
    implementation(Dependencies.network.okHttp.interceptor)
    // Compose
    implementation(Dependencies.compose.icons)
    implementation(Dependencies.compose.material)
    implementation(Dependencies.compose.activity)
    implementation(Dependencies.compose.navigation)
    implementation(Dependencies.compose.constraintLayout)
    implementation(Dependencies.compose.uiToolingPreview)
    //implementation(Dependencies.compose.ui)
    //implementation(Dependencies.compose.uiTest)
    // Test
    implementation(Dependencies.test.core)
    implementation(Dependencies.test.coreKtx)
    implementation(Dependencies.test.junit)
    // Accompanist
    implementation(Dependencies.accompanist.animation)
    implementation(Dependencies.accompanist.flowRow)
    implementation(Dependencies.accompanist.systemUiController)
    // Pretty time
    implementation(Dependencies.android.prettyTime)
    implementation(Dependencies.android.dataStore)
    implementation ("com.google.accompanist:accompanist-navigation-material:0.30.0")
}