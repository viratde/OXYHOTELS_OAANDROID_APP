package com.oxyhotel.feature_home.presenatation.utils

import com.oxyhotel.feature_home.domain.model.HotelStorage

data class HotelStorageFilter(
    val hotel: HotelStorage,
    val distance: Double
)