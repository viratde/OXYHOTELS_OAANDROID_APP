package com.oxyhotel.feature_home.domain.repositry


import com.oxyhotel.feature_home.domain.model.HotelStorage
import kotlinx.coroutines.flow.Flow

interface HotelRepository {

    fun getHotels(): Flow<List<HotelStorage>>

    suspend fun getHotelById(hotelId: String): HotelStorage?

    suspend fun addHotel(hotelStorage: HotelStorage)

    suspend fun deleteHotel(hotelStorage: HotelStorage)

    suspend fun addHotels(hotelStorages: List<HotelStorage>)

    suspend fun clearHotels()
    
    suspend fun bookMarkUpdate(hotelId: String, isWishlist: Boolean)
}