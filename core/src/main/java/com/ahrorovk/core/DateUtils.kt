package com.ahrorovk.core

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.graphics.convertTo
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Long.toMMDDYYYY(): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(this))
    /*return DateTimeFormatter.ofPattern("MM/dd/yyyy")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))*/
}

@SuppressLint("NewApi")
fun String.toMMDDYYYY(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return LocalDate.parse(this.substring(0, 10), formatter)
}
@RequiresApi(Build.VERSION_CODES.O)
fun Long.toMMDDYYYYLD():LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return LocalDate.parse(this.toMMDDYYYY().substring(0,10),formatter)
}
fun main(){
    var n = readln().toInt()
    for(i in 0 until n){
        var s = readln()
    }
}

fun Long.toMMMDD(): String {
    val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
    return sdf.format(Date(this))
    /*return DateTimeFormatter.ofPattern("MMM dd")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))*/
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toCurrentInMillis(): Long {
    val zonedDateTime = this.atStartOfDay(ZoneId.systemDefault())
    return zonedDateTime.toInstant().toEpochMilli()
}

fun Any.showLog(tag: String = "Hello") {
    Log.d(tag, this.toString())
}