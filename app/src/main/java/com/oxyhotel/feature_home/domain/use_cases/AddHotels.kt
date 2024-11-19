package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.repositry.HotelRepository

class AddHotels(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke(hotelStorages: List<HotelStorage>) {
        hotelRepository.addHotels(hotelStorages)
    }
}