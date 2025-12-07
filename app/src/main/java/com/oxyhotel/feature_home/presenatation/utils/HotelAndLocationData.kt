package com.oxyhotel.feature_home.presenatation.utils

import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.model.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelAndLocationData(
    @SerialName("locations")
    val locations: List<Location>,
    @SerialName("hotels")
    val hotels: List<HotelStorage>
)
