package com.oxyhotel.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = "Bookings"
)
@Serializable
data class BookingStorage(
    @SerialName("_id")
    @PrimaryKey val _id: String,
    @SerialName("hotelId")
    val hotelId: String,
    @SerialName("amount")
    val amount: Int,
    @SerialName("bookingAmount")
    val bookingAmount: Int,
    @SerialName("checkIn")
    val checkIn: String,
    @SerialName("checkOut")
    val checkOut: String,
    @SerialName("isCancelled")
    val isCancelled: Boolean,
    @SerialName("hasCheckedIn")
    val hasCheckedIn: Boolean,
    @SerialName("hasCheckedOut")
    val hasCheckedOut: Boolean,
    @SerialName("bookingId")
    val bookingId: String,
    @SerialName("hasNotShown")
    val hasNotShown: Boolean,
    @SerialName("bookedRooms")
    val bookedRooms: Map<String, List<Int>>
)
