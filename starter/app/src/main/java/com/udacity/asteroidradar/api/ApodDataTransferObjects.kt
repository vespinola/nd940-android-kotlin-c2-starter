package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.domain.Apod

data class NetworkPictureOfDay (
        val title: String,
        val url: String,
        @Json(name = "media_type") val mediaType: String,
)

fun NetworkPictureOfDay.asDomainModel(): Apod {
    return Apod(
            title = title,
            url = url,
            mediaType = mediaType
    )
}