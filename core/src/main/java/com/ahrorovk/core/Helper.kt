package com.ahrorovk.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.SimpleDateFormat

fun Long.toTimeHoursAndMinutes(): String {
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(this)
}

fun isInternetConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true

}