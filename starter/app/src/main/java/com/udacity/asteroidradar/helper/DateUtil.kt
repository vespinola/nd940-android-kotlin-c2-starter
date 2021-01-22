package com.udacity.asteroidradar.helper

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun format(date: Date, format: String = Constants.API_QUERY_DATE_FORMAT): String {
        return SimpleDateFormat(
                format, Locale.getDefault()
        ).format(date) ?: ""
    }
}