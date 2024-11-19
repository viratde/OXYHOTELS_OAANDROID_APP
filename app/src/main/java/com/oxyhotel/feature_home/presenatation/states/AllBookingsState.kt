package com.oxyhotel.feature_home.presenatation.states

import com.oxyhotel.feature_home.domain.model.BookingStorage


data class AllBookingsState(
    val isError: Boolean = false,
    val errorMessage: String = "",
    val bookings: List<BookingStorage> = mutableListOf(),
    val isRemoteDataLoading: Boolean = false,
    val isRemoteDataLoaded: Boolean = false,
    val filteredBy: String = "Upcoming",
    val cancellingId: String? = null
)