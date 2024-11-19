package com.oxyhotel.feature_auth.presentation.auth.states

data class SignupResponse(
    val authToken: String?,
    val email: String?,
    val phoneNumber: String?,
    val dob: String?,
    val gender: String?,
    val name: String?
)