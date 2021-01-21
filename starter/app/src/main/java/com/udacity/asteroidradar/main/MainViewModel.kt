package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.PictureOfDayApi
import com.udacity.asteroidradar.api.NetworkPictureOfDay
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val _apod = MutableLiveData<NetworkPictureOfDay>()
    val apod: LiveData<NetworkPictureOfDay>
        get() = _apod

    fun getApod() {
        viewModelScope.launch {
            try {
                val apod = PictureOfDayApi.RETROFIT_SERVICE.getPictureOfDay(Constants.API_KEY)

                if (apod.mediaType == "image") {
                    _apod.value = apod
                }
            } catch (e: Exception) {
                _apod.value = null
            }
        }
    }
}