package com.oxyhotel.feature_auth.presentation.auth.states

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SignupResponse(
    @SerialName("authToken")
    val authToken: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("phoneNumber")
    val phoneNumber: String?,
    @SerialName("dob")
    val dob: String?,
    @SerialName("gender")
    val gender: String?,
    @SerialName("name")
    val name: String?
)
