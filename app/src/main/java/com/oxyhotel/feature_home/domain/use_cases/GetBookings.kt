package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.repositry.BookingRepository
import kotlinx.coroutines.flow.Flow

class GetBookings(
    private val bookingRepository: BookingRepository
) {

    operator fun invoke(): Flow<List<BookingStorage>> {
        return bookingRepository.getBookings()
    }
}