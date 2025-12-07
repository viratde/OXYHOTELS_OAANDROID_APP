package com.oxyhotel.common.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyTokenRequest(
    @SerialName("authToken")
    val authToken: String?,
    @SerialName("fToken")
    val fToken: String
)

@Serializable
data class EmailOrPhoneRequest(
    @SerialName("data")
    val data: String
)

@Serializable
data class VerifyOtpRequest(
    @SerialName("otp")
    val otp: String,
    @SerialName("verifyToken")
    val verifyToken: String?
)

@Serializable
data class GoogleCodeRequest(
    @SerialName("code")
    val code: String
)

@Serializable
data class SignUpRequest(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("dob")
    val dob: String,
    @SerialName("verifiedToken")
    val verifiedToken: String
)

@Serializable
data class ProfileUpdateRequest(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("dob")
    val dob: String
)

@Serializable
data class ReviewRequest(
    @SerialName("hotelId")
    val hotelId: String,
    @SerialName("ratingNote")
    val ratingNote: String,
    @SerialName("ratingValue")
    val ratingValue: Int,
    @SerialName("bookingId")
    val bookingId: String
)
