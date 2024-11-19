package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.repositry.HotelRepository

class BookMarkHotel(
    private val hotelRepository: HotelRepository
) {

    suspend operator fun invoke(hotelId: String, isWishlist: Boolean) {
        hotelRepository.bookMarkUpdate(hotelId = hotelId, isWishlist = isWishlist)
    }
    
}