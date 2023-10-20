package com.ahrorovk.tasbihfarzun.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import com.ahrorovk.tasbihfarzun.app.navigation.Navigation
import com.ahrorovk.tasbihfarzun.data.network.dto.PostResponse
import com.ahrorovk.tasbihfarzun.app.ui.theme.TasbihFarzunTheme
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Thread.sleep

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
    val scope = CoroutineScope(Dispatchers.Main)
    private val service = PostApi.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val post = produceState<List<PostResponse>>(
                initialValue = emptyList(),
                producer = {
                    value = service.getPosts()
                }
            )
            TasbihFarzunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        var a = scope.isActive
//        Log.e("JOB_CANCEL","->$a")
//        scope.cancel()
//        a=scope.isActive
//        Log.e("JOB_CANCEL","->$a")
//    }
}

@Composable
fun WebView_(siteName: WebViewState) {
    WebView(
        state = siteName,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                settings.loadsImagesAutomatically = true
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.allowFileAccess = true
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
                CookieManager.getInstance().setAcceptCookie(true)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                webViewClient = object : AccompanistWebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        val url = request.url.toString()
                        if (url.startsWith("tel:")) {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse(url)
                            view.context.startActivity(intent)
                            return true
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                webChromeClient = object : AccompanistWebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                        Log.d(
                            "WebView",
                            "${consoleMessage.message()} -- From line ${consoleMessage.lineNumber()} of ${consoleMessage.sourceId()}"
                        )
                        return true
                    }
                }
            }
        })

}


suspend fun fetchFiles() = withContext(Dispatchers.IO) {
    coroutineScope {
        launch {
            fetchFile1()
        }
        launch {
            fetchFile2()
        }
    }
}


suspend fun fetchFile1() = withContext(Dispatchers.IO) {
    Log.e("Fetch", "starting download file-1")
    delay(3_000)
    Log.e("Fetch", "file-1 download finished")
}

suspend fun fetchFile2() = withContext(Dispatchers.IO) {
    Log.e("Fetch", "starting download file-2")
    delay(3_000)
    Log.e("Fetch", "file-2 download finished")
}

class MyHandler() : Handler(Looper.getMainLooper()) {
    var handle = "stoped"
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val run = Runnable1(msg)
        run.run()
//        val thread = Thread1(msg)
//        thread.start()
//        thread.join()
//        handle = msg.data.getString("1","thread")
//        Log.e("TAG",handle)
    }

    fun printMessage(msg: Message, key: String, message: String) {
        msg.data.putString(key, message)
    }

    inner class Thread1(message: Message) : Thread() {
        val msg = message
        override fun run() {
            super.run()
            Log.e("TAG", "started")
            sleep(6000)
            printMessage(msg, "1", "thread run")
            Log.e("TAG", "finished")
        }
    }

    inner class Runnable1(message: Message) : Runnable {
        val mes = message
        override fun run() {
            Log.e("TAG", "started")
            sleep(4000)
            printMessage(mes, "2", "runnable run")
            Log.e("TAG", "finished")
        }
    }
}

@Composable
fun Greeting(name: String, onCLick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello $name!")
        Button(onClick = onCLick) {
        }
    }
}

