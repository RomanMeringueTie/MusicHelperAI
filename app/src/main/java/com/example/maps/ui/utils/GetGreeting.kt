package com.example.maps.ui.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("SimpleDateFormat")
fun getGreeting(name: String): String {
    val sdf = SimpleDateFormat("HH", Locale.getDefault())
    val currentTime = Calendar.getInstance().time
    val currentHour = sdf.format(currentTime).toInt()
    val greeting = if (currentHour < 5) {
        "Доброй ночи"
    } else if (currentHour < 12) {
        "Доброе утро"
    } else if (currentHour < 18) {
        "Добрый день"
    } else "Добрый вечер"
    return "$greeting, $name!"
}