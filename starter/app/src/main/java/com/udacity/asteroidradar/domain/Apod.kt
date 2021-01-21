package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Apod(
        val title: String,
        val url: String,
        val mediaType: String,
): Parcelable