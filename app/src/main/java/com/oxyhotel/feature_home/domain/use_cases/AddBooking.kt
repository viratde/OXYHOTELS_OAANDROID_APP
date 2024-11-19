package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.repositry.BookingRepository

class AddBooking(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke(bookingStorage: BookingStorage) {
        bookingRepository.addBooking(bookingStorage = bookingStorage)
    }

}