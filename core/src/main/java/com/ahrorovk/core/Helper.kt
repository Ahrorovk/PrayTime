package com.ahrorovk.core

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.ahrorovk.core.model.Time
import com.ahrorovk.model.local.pray_time.PrayerTimesEntity
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

fun Long.toTimeHoursAndMinutes(): String {
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(this)
}


fun doesScreenHaveBottomBar(currentScreen: String) = true


@RequiresApi(Build.VERSION_CODES.O)
fun isDateDifferent(selectedDate: Long, lastDateFromDb: String): Boolean {
    val _selectedDate = selectedDate.toMMDDYYYY().toMMDDYYYY()
    val _lastDateFromDb = lastDateFromDb.toMMDDYYYY()
    return _lastDateFromDb.year < _selectedDate.year || _lastDateFromDb.monthValue < _selectedDate.monthValue
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

fun getListOfTimeStates(times: PrayerTimesEntity) = listOf(
    Time("Fajr", times.fajrTime, R.drawable.fajr),
    Time("Zuhr", times.zuhrTime, R.drawable.zuhr),
    Time("Asr", times.asrTime, R.drawable.asr),
    Time("Maghrib", times.magribTime, R.drawable.maghrib),
    Time("Isha", times.ishaTime, R.drawable.isha)
)
fun getListOfTimes(times: PrayerTimesEntity) = listOf(
     times.fajrTime,
     times.zuhrTime,
     times.asrTime,
     times.magribTime,
     times.ishaTime
)

@SuppressLint("NewApi")
fun parseEvent(eventData: List<String>): List<Int> {
    val parsedTime: Array<LocalTime> = Array(eventData.size) { LocalTime.now() }
    val parsedTimeResult: Array<Int> = Array(eventData.size) { 0 }
    eventData.forEachIndexed { ind, it ->
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        parsedTime[ind] = LocalTime.parse(it.substring(0, 5), formatter)
        parsedTimeResult[ind] = (parsedTime[ind].hour * 60) + parsedTime[ind].minute
    }
    return parsedTimeResult.toList()
}

@SuppressLint("NewApi")
fun findMinDifference(currentTime: LocalTime, events: List<String>): Int {
    val parsedEvents = parseEvent(events).map { minutesUntilEvent(currentTime, it) }
    var minElement = Int.MAX_VALUE
    var minIndex = 0
    parsedEvents.forEachIndexed { ind, element ->
        element?.let {
            if (it < minElement) {
                minElement = it
                minIndex = ind
            }
        }
    }
    return minIndex
}

@SuppressLint("NewApi")
fun minutesUntilEvent(currentTime: LocalTime, minutes: Int): Int? {
    val currentMinutes = currentTime.hour * 60 + currentTime.minute

    return if (minutes >= currentMinutes)
        minutes - currentMinutes
    else
        null
}
