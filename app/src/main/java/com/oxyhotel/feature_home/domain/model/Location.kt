package com.oxyhotel.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(
    tableName = "locations"
)
@Serializable
data class Location(
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("distance")
    val distance: Double,
    @SerialName("name")
    val name: String,
    @SerialName("_id")
    @PrimaryKey val _id: String
)
