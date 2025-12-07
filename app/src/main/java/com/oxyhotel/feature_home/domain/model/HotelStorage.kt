package com.oxyhotel.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oxyhotel.common.serialization.DateSerializer
import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Entity(
    tableName = "hotelStorage"
)
@Serializable
data class HotelStorage(

    @SerialName("_id")
    @PrimaryKey val _id: String = "",
    @SerialName("hotelName")
    val hotelName: String = "",
    @SerialName("hotelId")
    val hotelId: String = "",
    @SerialName("phoneNo")
    val phoneNo: String = "",
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("locationUrl")
    val locationUrl: String = "",
    @SerialName("hotelAddress")
    val hotelAddress: String = "",
    @SerialName("hotelDescription")
    val hotelDescription: String = "",
    @SerialName("refundPercentage")
    var refundPercentage: Int,
    @SerialName("checkIn")
    val checkIn: String = "12-00",
    @SerialName("checkOut")
    val checkOut: String = "11-00",
    @SerialName("minPrice")
    val minPrice: Int = 0,
    @SerialName("maxPrice")
    val maxPrice: Int = 0,


    @SerialName("imageData")
    val imageData: Map<String, List<String>> = emptyMap(),
    @SerialName("nearBy")
    val nearBy: Map<String, List<String>> = emptyMap(),
    @SerialName("restrictions")
    val restrictions: List<String> = emptyList(),
    @SerialName("housePoliciesDos")
    val housePoliciesDos: List<String> = emptyList(),
    @SerialName("housePoliciesDonts")
    val housePoliciesDonts: List<String> = emptyList(),
    @SerialName("houseAmenities")
    val houseAmenities: List<String> = emptyList(),
    @SerialName("roomTypes")
    val roomTypes: Map<String, Map<String, JsonElement>> = emptyMap(),
    @SerialName("hotelStructure")
    val hotelStructure: Map<String, List<ActualRoom>> = emptyMap(),
    @SerialName("timezone")
    val timezone: String,

    @SerialName("isWishlist")
    val isWishlist: Boolean = false,
    @SerialName("noOfRatings")
    val noOfRatings: Int = 0,
    @SerialName("rating")
    val rating: Float = 0f,
    @SerialName("reviews")
    val reviews: List<Review> = emptyList(),
    @SerialName("prices")
    val prices: Map<String, Map<String, Map<String, Double>>> = emptyMap()
)

@Serializable
data class ActualRoom(
    @SerialName("roomNo")
    val roomNo: String,
    @SerialName("roomType")
    val roomType: String
)

@Serializable
data class Review(
    @SerialName("name")
    val name: String,
    @SerialName("phoneNumber")
    val phoneNumber: String,
    @SerialName("ratingLevel")
    val ratingLevel: Int,
    @SerialName("date")
    @Serializable(with = DateSerializer::class)
    val date: Date,
    @SerialName("bookingId")
    val bookingId: String,
    @SerialName("ratingNote")
    val ratingNote: String
)
