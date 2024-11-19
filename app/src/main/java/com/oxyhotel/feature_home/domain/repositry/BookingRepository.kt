package com.oxyhotel.feature_home.domain.repositry

import com.oxyhotel.feature_home.domain.model.BookingStorage
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    fun getBookings(): Flow<List<BookingStorage>>

    suspend fun getBookingById(bookingId: Int): BookingStorage?

    suspend fun addBooking(bookingStorage: BookingStorage)

    suspend fun deleteBooking(bookingStorage: BookingStorage)

    suspend fun addBookings(bookingStorages: List<BookingStorage>)

    suspend fun clearBookings()

}