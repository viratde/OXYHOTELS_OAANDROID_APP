package com.oxyhotel.feature_home.domain.use_cases

data class BookingUseCases(
    val addBooking: AddBooking,
    val addBookings: AddBookings,
    val getBooking: GetBookings,
    val getBookingById: GetBookingById,
    val clearBookings: ClearBookings
)