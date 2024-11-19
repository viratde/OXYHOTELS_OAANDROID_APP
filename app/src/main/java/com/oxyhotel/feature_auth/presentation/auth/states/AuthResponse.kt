package com.oxyhotel.feature_auth.presentation.auth.states

data class AuthResponse(
    val status: Boolean,
    val message: String,
    val data: String?,
)