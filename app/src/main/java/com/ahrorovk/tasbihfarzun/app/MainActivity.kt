package com.ahrorovk.tasbihfarzun.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.ahrorovk.core.DataStoreManager
import com.ahrorovk.core.getLanguages
import com.ahrorovk.settings.LocaleHelper
import com.ahrorovk.tasbihfarzun.app.navigation.Navigation
import com.ahrorovk.tasbihfarzun.app.ui.theme.TasbihFarzunTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.util.Locale

val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
    Log.e("exception Handler", "-> ${throwable.message}\n${coroutineContext.isActive}")
}
val coroutine = CoroutineScope(Dispatchers.Default + SupervisorJob() + handlerException)
suspend fun methodOfException() {
    throw IOException()
}

fun doWork() {
    coroutine.launch {
        methodOfException()
        Log.e("SCope_IsActive", "isActive->${coroutine.isActive}")
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val dataStoreManager = DataStoreManager(newBase)

        val context = runBlocking {
            val languageIndex = dataStoreManager.getLanguageState.first()
            val langCode = getLanguages()[languageIndex].shortCut
            val locale = Locale(langCode)
            LocaleHelper.setLocale(newBase, locale)
        }

        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Permissions()
            TasbihFarzunTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}