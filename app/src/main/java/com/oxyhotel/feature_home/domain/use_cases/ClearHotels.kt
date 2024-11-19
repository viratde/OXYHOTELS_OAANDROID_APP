package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.repositry.HotelRepository

class ClearHotels(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke() {
        hotelRepository.clearHotels()
    }
}