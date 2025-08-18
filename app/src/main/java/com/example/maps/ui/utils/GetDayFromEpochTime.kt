package com.example.maps.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getDayTimeFromEpochTime(epochTime: Long, locale: Locale = Locale.getDefault()): String {
    val date = Date(epochTime)
    val format = SimpleDateFormat("HH:mm", locale)
    val dayTime = format.format(date)
    return dayTime
}