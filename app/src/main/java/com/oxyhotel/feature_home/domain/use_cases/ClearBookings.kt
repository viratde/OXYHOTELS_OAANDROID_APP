package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.repositry.BookingRepository

class ClearBookings(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke() {
        bookingRepository.clearBookings()
    }

}