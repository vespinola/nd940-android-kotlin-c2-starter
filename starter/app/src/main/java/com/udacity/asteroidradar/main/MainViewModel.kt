package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.PictureOfDayApi
import com.udacity.asteroidradar.api.NetworkPictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application.applicationContext)
    private val repository = AsteroidsRepository(database)

    private val _pictureOfDay = MutableLiveData<NetworkPictureOfDay>()
    val pictureOfDay: LiveData<NetworkPictureOfDay>
        get() = _pictureOfDay

    val asteroids = repository.asteroids

    init {
        getPictureOfDay()

        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                val apod = PictureOfDayApi.service.getPictureOfDay(Constants.API_KEY)

                if (apod.mediaType == "image") {
                    _pictureOfDay.value = apod
                }
            } catch (e: Exception) {
                _pictureOfDay.value = null
            }
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }

    }
}