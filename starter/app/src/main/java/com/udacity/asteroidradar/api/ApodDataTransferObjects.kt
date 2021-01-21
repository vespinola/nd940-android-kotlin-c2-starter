package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.domain.Apod

data class NetworkApod (
        val title: String,
        val url: String,
        @Json(name = "media_type") val mediaType: String,
)

fun NetworkApod.asDomainModel(): Apod {
    return Apod(
            title = title,
            url = url,
            mediaType = mediaType
    )
}