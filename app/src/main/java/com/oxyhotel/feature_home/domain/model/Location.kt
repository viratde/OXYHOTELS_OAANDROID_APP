package com.oxyhotel.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "locations"
)
data class Location(
    val longitude: Double,
    val latitude: Double,
    val distance: Double,
    val name: String,
    @PrimaryKey val _id: String
)