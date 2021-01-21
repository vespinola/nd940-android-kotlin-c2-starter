package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.ApodApi
import com.udacity.asteroidradar.api.NetworkApod
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val _apod = MutableLiveData<NetworkApod>()
    val apod: LiveData<NetworkApod>
        get() = _apod

    fun getApod() {
        viewModelScope.launch {
            try {
                val apod = ApodApi.retrofitService.getApod(Constants.API_KEY)

                if (apod.mediaType == "image") {
                    _apod.value = apod
                }
            } catch (e: Exception) {
                _apod.value = null
            }
        }
    }
}