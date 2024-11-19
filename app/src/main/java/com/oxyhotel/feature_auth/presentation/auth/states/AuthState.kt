package com.oxyhotel.feature_auth.presentation.auth.states


data class AuthState(
    val isAuthenticating: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isSaving: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isOtpSent: Boolean = false,
    val otpSentEmail: String? = null,
    val otpToken: String? = null,
    val isUpdateRequired: Boolean = false,
    val isVerified: Boolean = false,
    val verificationResponse: VerificationResponse = VerificationResponse()
)