package com.oxyhotel.feature_home.presenatation.states

data class BookingState(
    val checkInTime: String,
    val checkOutTime: String,
    val amount: Int? = null,
    val isCalculating: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val rooms: Map<String, List<Int>> = mapOf()
)

data class BookingRoomData(
    val roomType: String,
    val rooms: Int,
    val noOfGuests: MutableList<Int> = mutableListOf()
)