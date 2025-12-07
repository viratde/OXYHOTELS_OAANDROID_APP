package com.oxyhotel.feature_auth.presentation.auth.states

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AuthResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: String?,
    @SerialName("isANewVersionInReview")
    val isANewVersionInReview: Boolean = false
)