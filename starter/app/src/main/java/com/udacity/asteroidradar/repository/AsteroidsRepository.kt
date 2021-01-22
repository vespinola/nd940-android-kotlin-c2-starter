package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.helper.DateUtil
import com.udacity.asteroidradar.helper.add
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    val todayAsteroids: LiveData<List<Asteroid>>
        get() {
            val today = Date()
            val formattedToday = DateUtil.format(today)
            return Transformations.map(database.asteroidDao.getTodayAsteroids(formattedToday)) {
                it.asDomainModel()
            }
        }

    val weeklyAsteroids: LiveData<List<Asteroid>>
        get() {
            val startDate = Date()
            val endDate = startDate.add(Constants.DEFAULT_END_DATE_DAYS)
            val formattedStartDate = DateUtil.format(startDate)
            val formattedEndDate = DateUtil.format(endDate)
            return Transformations.map(database.asteroidDao.getWeeklyAsteroids(
                    startDate = formattedStartDate,
                    endDate = formattedEndDate
            )) {
                it.asDomainModel()
            }
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroids = AsteroidApi.service.getAsteroids()
                val parsedResult = parseAsteroidsJsonResult(jsonResult = JSONObject(asteroids))
                database.asteroidDao.insertAll(*parsedResult.asDatabaseModel())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}