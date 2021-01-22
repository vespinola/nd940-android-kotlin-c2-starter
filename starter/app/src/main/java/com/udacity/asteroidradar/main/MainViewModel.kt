package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApiFilter
import com.udacity.asteroidradar.api.PictureOfDayApi
import com.udacity.asteroidradar.api.NetworkPictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application.applicationContext)
    private val repository = AsteroidsRepository(database)

    private val _pictureOfDay = MutableLiveData<NetworkPictureOfDay>()
    val pictureOfDay: LiveData<NetworkPictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetail = MutableLiveData<Asteroid>()
    val navigateToDetail: LiveData<Asteroid>
        get() = _navigateToDetail

    private val _filterType = MutableLiveData<AsteroidApiFilter>()

    init {
        getPictureOfDay()

        viewModelScope.launch {
            repository.refreshAsteroids()
        }

        updateList(AsteroidApiFilter.SAVED)
    }

    val asteroids = Transformations.switchMap(_filterType) {
        when(it) {
            AsteroidApiFilter.WEEKLY -> {
                repository.weeklyAsteroids
            }
            AsteroidApiFilter.TODAY -> {
                repository.todayAsteroids
            }
            else -> {
                repository.asteroids
            }
        }
    }

    fun updateList(filter: AsteroidApiFilter) {
        _filterType.value = filter
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

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToDetail.value = null
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