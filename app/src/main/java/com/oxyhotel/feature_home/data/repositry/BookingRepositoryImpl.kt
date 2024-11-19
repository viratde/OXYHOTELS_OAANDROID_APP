package com.oxyhotel.feature_home.data.repositry

import com.oxyhotel.feature_home.data.data_source.HotelDao
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.repositry.BookingRepository
import kotlinx.coroutines.flow.Flow

class BookingRepositoryImpl(
    private val hotelDao: HotelDao
) : BookingRepository {
    override fun getBookings(): Flow<List<BookingStorage>> {
        return hotelDao.getBookings()
    }

    override suspend fun getBookingById(bookingId: Int): BookingStorage? {
        return hotelDao.getBookingById(bookingId = bookingId)
    }

    override suspend fun addBooking(bookingStorage: BookingStorage) {
        return hotelDao.addBooking(bookingStorage)
    }

    override suspend fun deleteBooking(bookingStorage: BookingStorage) {
        return hotelDao.deleteBooking(bookingStorage = bookingStorage)
    }

    override suspend fun addBookings(bookingStorages: List<BookingStorage>) {
        hotelDao.addBookings(bookingStorages)
    }

    override suspend fun clearBookings() {
        hotelDao.clearBookings()
    }


}