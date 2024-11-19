package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.repositry.HotelRepository
import kotlinx.coroutines.flow.Flow

class GetHotel(
    private val hotelRepository: HotelRepository
) {

    operator fun invoke(): Flow<List<HotelStorage>> {
        return hotelRepository.getHotels()
    }
}