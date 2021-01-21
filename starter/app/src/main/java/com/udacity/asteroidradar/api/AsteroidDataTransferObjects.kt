package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroid

data class NearEarthObject (
    val id: String,
    @Json(name = "absolute_magnitude_h") val absoluteMagnitudeH: Double,
    @Json(name = "estimated_diameter_max") val estimatedDiameter: EstimatedDiameter,
    @Json(name = "is_potentially_hazardous_asteroid") val isPotentiallyHazardousAsteroid: Boolean,
    @Json(name = "close_approach_data") val closeApproachData: CloseApproachData
)

data class EstimatedDiameter(
    @Json(name = "kilometers") val kilometers: Kilometers
)

data class Kilometers(
    @Json(name = "estimated_diameter_max") val estimatedDiameterMax: Double,
    @Json(name = "estimated_diameter_min") val estimatedDiameterMin: Double,
)

data class CloseApproachData(
    val list: List<CloseApproachDataItem>
)

data class CloseApproachDataItem(
    @Json(name = "relative_velocity") val relativeVelocity: RelativeVelocity,
    @Json(name = "miss_distance") val missDistance: MissDistance
)

data class RelativeVelocity(
    @Json(name = "kilometers_per_second") val kilometersPerSecond: String
)

data class MissDistance(
    val astronomical: String
)

fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
