package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.domain.PictureOfDay

data class NetworkPictureOfDay (
        val title: String,
        val url: String,
        @Json(name = "media_type") val mediaType: String,
)