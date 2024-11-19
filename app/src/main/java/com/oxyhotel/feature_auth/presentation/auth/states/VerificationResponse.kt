package com.oxyhotel.feature_auth.presentation.auth.states

data class VerificationResponse(
    val authToken: String? = null,
    val verifiedToken: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val dob: String? = null,
    val gender: String? = null,
    val name: String? = null
)