package com.udacity.asteroidradar.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


fun getClient() : OkHttpClient {
    val logging = HttpLoggingInterceptor()

    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}