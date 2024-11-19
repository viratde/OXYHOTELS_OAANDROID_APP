package com.oxyhotel.feature_home.domain.use_cases

data class HotelUseCases(
    val addHotel: AddHotel,
    val getHotel: GetHotel,
    val addHotels: AddHotels,
    val clearHotels: ClearHotels,
    val getHotelById: GetHotelById,
    val bookMarkHotel: BookMarkHotel
)