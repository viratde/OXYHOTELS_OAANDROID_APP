package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.repositry.BookingRepository

class GetBookingById(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke(bookingId: Int): BookingStorage? {
        return bookingRepository.getBookingById(bookingId = bookingId)
    }

}