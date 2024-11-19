package com.oxyhotel.feature_home.data.repositry

import com.oxyhotel.feature_home.data.data_source.HotelDao
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.repositry.HotelRepository
import kotlinx.coroutines.flow.Flow

class HotelRepositoryImpl(
    private val hotelDao: HotelDao
) : HotelRepository {

    override fun getHotels(): Flow<List<HotelStorage>> {
        return hotelDao.getHotels()
    }

    override suspend fun getHotelById(hotelId: String): HotelStorage? {
        return hotelDao.getHotelById(hotelId = hotelId)
    }

    override suspend fun addHotel(hotelStorage: HotelStorage) {
        hotelDao.addHotel(hotelStorage)
    }

    override suspend fun deleteHotel(hotelStorage: HotelStorage) {
        hotelDao.deleteHotel(hotelStorage)
    }

    override suspend fun clearHotels() {
        hotelDao.clearHotels()
    }

    override suspend fun bookMarkUpdate(hotelId: String, isWishlist: Boolean) {
        hotelDao.bookMarkUpdate(hotelId = hotelId, isWishlist = isWishlist)
    }

    override suspend fun addHotels(hotelStorages: List<HotelStorage>) {
        hotelDao.addHotels(hotelStorages)
    }

}