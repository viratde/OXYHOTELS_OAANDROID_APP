package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.repositry.BookingRepository

class AddBookings(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke(bookingStorages: List<BookingStorage>) {
        bookingRepository.addBookings(bookingStorages = bookingStorages)
    }

}