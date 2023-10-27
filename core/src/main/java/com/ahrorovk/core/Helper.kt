package com.ahrorovk.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.SimpleDateFormat
import java.util.Calendar

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

fun isTodayGreaterThanYesterday(
    todayTimestamp: Long = System.currentTimeMillis(),
    yesterdayTimestamp: Long
): Boolean {
    val todayCalendar = Calendar.getInstance().apply {
        timeInMillis = todayTimestamp
    }
    val yesterdayCalendar = Calendar.getInstance().apply {
        timeInMillis = yesterdayTimestamp
        add(
            Calendar.DAY_OF_YEAR,
            1
        ) // Прибавляем 1 день к yesterday, чтобы сравнивать сегодня с завтрашним днем
    }
    return todayCalendar > yesterdayCalendar
}

fun dateFormatter(state: Int): String {
    return if (state <= 9)
        "0$state"
    else "$state"
}