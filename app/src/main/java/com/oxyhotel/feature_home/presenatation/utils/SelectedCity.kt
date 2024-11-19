package com.oxyhotel.feature_home.presenatation.utils

import com.oxyhotel.feature_home.domain.model.Location

sealed interface SelectedCity {
    object All : SelectedCity
    object NearBy : SelectedCity
    data class SelectedLocation(val location: Location) : SelectedCity
}