package com.udacity.asteroidradar.api

import com.squareup.moshi.Json

data class Apod (
        val title: String,
        val url: String,
        @Json(name = "media_type") val mediaType: String,
)
