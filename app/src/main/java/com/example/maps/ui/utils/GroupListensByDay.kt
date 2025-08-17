package com.example.maps.ui.utils

import com.example.maps.data.model.Day
import com.example.maps.data.model.ListenFull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun groupListensByDay(listens: List<ListenFull>, locale: Locale = Locale.getDefault()): List<Day> {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", locale)

    return listens
        .groupBy { listen ->
            val date = Date(listen.playedAt)
            dateFormat.format(date)
        }
        .map { (date, listensForDay) ->
            Day(date, listensForDay.sortedBy { it.playedAt })
        }
        .sortedByDescending { day ->
            dateFormat.parse(day.date)?.time ?: 0L
        }
}