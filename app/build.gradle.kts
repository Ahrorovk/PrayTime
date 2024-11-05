plugins {
    id(GradlePlugin.ANDROID_APPLICATION)
    id(GradlePlugin.KOTLIN_ANDROID)
    id(GradlePlugin.KAPT)
    id(GradlePlugin.DAGGER_HILT)
    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
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
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":features:prayertimes")))
    implementation(project(mapOf("path" to ":features:zikr")))
    implementation(project(mapOf("path" to ":local")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(":model"))
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
    //WebView
    implementation ("com.google.accompanist:accompanist-webview:0.31.4-beta")
    implementation ("androidx.webkit:webkit:1.7.0")
    // Pretty time
    implementation(Dependencies.android.prettyTime)
    implementation(Dependencies.android.dataStore)
    implementation ("com.google.accompanist:accompanist-navigation-material:0.30.0")
    // Ktor
    implementation(Dependencies.network.ktor.core)
    implementation(Dependencies.network.ktor.cio)
    implementation(Dependencies.network.ktor.android)
    implementation(Dependencies.network.ktor.serialization)
    implementation(Dependencies.network.ktor.websockets)
    implementation(Dependencies.network.ktor.logging)
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation(Dependencies.imageLoader.compose)
    implementation(Dependencies.imageLoader.gif)
    // WorkManager with Coroutines
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    // Ads
//    implementation(Dependencies.googleServices.ads)
    //Firebase
//    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.4")
//    implementation(platform("com.google.firebase:firebase-bom:31.3.0"))
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-crashlytics-ktx")
}