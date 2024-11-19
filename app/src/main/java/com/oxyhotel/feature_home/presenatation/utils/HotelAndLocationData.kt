package com.oxyhotel.feature_home.presenatation.utils

import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.model.Location

data class HotelAndLocationData(
    val locations: List<Location>, val hotels: List<HotelStorage>
)