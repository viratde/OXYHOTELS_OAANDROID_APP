package com.oxyhotel.constants

object Constant {
    const val domain = "https://api.oxyhotels.com"


    const val currentVersion = "3.0.3"


    const val getOtpOnEmailOrPhone = "$domain/auth"

    const val verifyOtpRoute = "$domain/auth/verify"

    const val createAccountRoute = "$domain/auth/createAccount"

    const val verifyTokenRoute = "$domain/api/verifyAuthToken"

    const val verifyGoogleCodeRoute = "$domain/auth/google/verify"


    const val getHotelRoute = "$domain/api/getHotels"

    const val getAllBookings = "$domain/api/getBookings"

    const val updateProfileRoute = "$domain/api/updateUser"

    const val cancelBookingRoute = "$domain/api/cancelBooking"

    const val createBookingRoute = "$domain/api/createBooking"

    const val postReviewRoute = "$domain/api/postReview"
}