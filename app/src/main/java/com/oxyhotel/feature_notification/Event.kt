package com.oxyhotel.feature_notification

import com.oxyhotel.feature_home.domain.model.BookingStorage

data class Event(
    val event: Events,
    val bookingModel: BookingStorage
)