package com.oxyhotel.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Bookings"
)
data class BookingStorage(
    @PrimaryKey val _id: String,
    val hotelId: String,
    val amount: Int,
    val bookingAmount: Int,
    val checkIn: String,
    val checkOut: String,
    val isCancelled: Boolean,
    val hasCheckedIn: Boolean,
    val hasCheckedOut: Boolean,
    val bookingId: String,
    val hasNotShown: Boolean,
    val bookedRooms: Map<String, List<Int>>
)
