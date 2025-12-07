package com.oxyhotel.feature_home.presenatation.states

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingState(
    @SerialName("checkInTime")
    val checkInTime: String,
    @SerialName("checkOutTime")
    val checkOutTime: String,
    @SerialName("amount")
    val amount: Int? = null,
    @SerialName("isCalculating")
    val isCalculating: Boolean = false,
    @SerialName("isError")
    val isError: Boolean = false,
    @SerialName("errorMessage")
    val errorMessage: String = "",
    @SerialName("rooms")
    val rooms: Map<String, List<Int>> = mapOf()
)

@Serializable
data class BookingRoomData(
    @SerialName("roomType")
    val roomType: String,
    @SerialName("rooms")
    val rooms: Int,
    @SerialName("noOfGuests")
    val noOfGuests: MutableList<Int> = mutableListOf()
)
