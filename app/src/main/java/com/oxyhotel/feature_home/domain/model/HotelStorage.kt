package com.oxyhotel.feature_home.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "hotelStorage"
)
data class HotelStorage(

    @PrimaryKey val _id: String = "",
    val hotelName: String = "",
    val hotelId: String = "",
    val phoneNo: String = "",
    val latitude: Double,
    val longitude: Double,
    val locationUrl: String = "",
    val hotelAddress: String = "",
    val hotelDescription: String = "",
    var refundPercentage: Int,
    val checkIn: String = "12-00",
    val checkOut: String = "11-00",
    val minPrice: Int = 0,
    val maxPrice: Int = 0,


    val imageData: Map<String, List<String>> = mapOf(),
    val nearBy: Map<String, List<String>> = mapOf(),
    val restrictions: List<String> = listOf(),
    val housePoliciesDos: List<String> = listOf(),
    val housePoliciesDonts: List<String> = listOf(),
    val houseAmenities: List<String> = listOf(),
    val roomTypes: Map<String, Map<String, Any>> = mapOf(),
    val hotelStructure: Map<String, List<ActualRoom>> = mapOf(),
    val timezone: String,

    val isWishlist: Boolean = false,
    val noOfRatings: Int = 0,
    val rating: Float = 0f,
    val reviews: MutableList<Review> = mutableListOf(),
    val prices: MutableMap<String, MutableMap<String, MutableMap<String, Number>>> = mutableMapOf()
)

data class ActualRoom(
    val roomNo: String,
    val roomType: String
)

data class Review(
    val name: String,
    val phoneNumber: String,
    val ratingLevel: Int,
    val date: Date,
    val bookingId: String,
    val ratingNote: String
)