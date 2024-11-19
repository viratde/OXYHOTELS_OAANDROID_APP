package com.oxyhotel.feature_home.presenatation.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun distanceBetweenLocations(location1: UserLocation, location2: UserLocation): Double {
    val distance1 = calculateDistance(location1, location2)
    val distance2 = calculateDistance(location2, location1)
    return minOf(distance1, distance2)
}

private fun calculateDistance(location1: UserLocation, location2: UserLocation): Double {
    val earthRadius = 6371 // Earth's radius in kilometers

    val lat1 = Math.toRadians(location1.latitude)
    val lat2 = Math.toRadians(location2.latitude)
    val lon1 = Math.toRadians(location1.longitude)
    val lon2 = Math.toRadians(location2.longitude)

    val dLat = lat2 - lat1
    val dLon = lon2 - lon1

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1) * cos(lat2) * sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}

