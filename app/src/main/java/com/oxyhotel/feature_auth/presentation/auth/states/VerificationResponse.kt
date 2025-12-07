package com.oxyhotel.feature_auth.presentation.auth.states

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class VerificationResponse(
    @SerialName("authToken")
    val authToken: String? = null,
    @SerialName("verifiedToken")
    val verifiedToken: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("phoneNumber")
    val phoneNumber: String? = null,
    @SerialName("dob")
    val dob: String? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("name")
    val name: String? = null
)
