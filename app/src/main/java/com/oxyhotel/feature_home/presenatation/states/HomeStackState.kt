package com.oxyhotel.feature_home.presenatation.states

import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.model.Location
import com.oxyhotel.feature_home.presenatation.utils.SelectedCity
import com.oxyhotel.feature_home.presenatation.utils.UserLocation

data class HomeStackState(
    val hotelsData: List<HotelStorage> = listOf(),
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isRemoteHotelLoading: Boolean = false,
    val isRemoteHotelLoaded: Boolean = false,
    val isLocalHotelLoading: Boolean = false,
    val isLocalHotelLoaded: Boolean = false,
    val isPricingLoading: Boolean = false,
    val isPricingLoaded: Boolean = false,
    val userLocation: UserLocation? = null,
    val selectedCity: SelectedCity = SelectedCity.All,
    val hasLocationPermission: Boolean = false,
    val showHotels: List<HotelStorage> = listOf(),
    val locations: List<Location> = listOf()
)

