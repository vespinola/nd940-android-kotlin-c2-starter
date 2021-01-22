package com.udacity.asteroidradar.helper

import java.util.*

fun Date.add(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, days)
    return calendar.time
}