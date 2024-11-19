package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.repositry.HotelRepository

class GetHotelById(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke(hotelId: String): HotelStorage? {
        return hotelRepository.getHotelById(hotelId)
    }

}