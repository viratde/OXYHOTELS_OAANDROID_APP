package com.oxyhotel.feature_home.presenatation.states

import com.oxyhotel.feature_home.presenatation.utils.RatingLevel

data class RatingState(
    val ratingLevel: RatingLevel = RatingLevel.FOURTH,
    val ratingNote: String = "The hotel was amazing and provides very good service.",
    val isLoading: Boolean = false,
    val isError: Boolean = true,
    val errorMessage: String = ""
)