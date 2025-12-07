package com.oxyhotel.feature_home.data.data_source

import androidx.room.TypeConverter
import com.oxyhotel.feature_home.domain.model.ActualRoom
import com.oxyhotel.feature_home.domain.model.Review
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.util.Date

class TypeConverters {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @TypeConverter
    fun fromStringToArray(value: String): List<String> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromArrayToString(value: List<String>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun fromStringToMap(value: String): Map<String, List<String>> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMapToString(value: Map<String, List<String>>): String {
        return json.encodeToString(value)
    }


    @TypeConverter
    fun fromRoomTypes(roomTypes: Map<String, Map<String, JsonElement>>): String {
        return json.encodeToString(roomTypes)
    }

    @TypeConverter
    fun toRoomTypes(roomTypes: String): Map<String, Map<String, JsonElement>> {
        return json.decodeFromString(roomTypes)
    }

    @TypeConverter
    fun fromFloorData(structure: Map<String, List<ActualRoom>>): String {
        return json.encodeToString(structure)
    }

    @TypeConverter
    fun toFloorData(data: String): Map<String, List<ActualRoom>> {
        return json.decodeFromString(data)
    }


    @TypeConverter
    fun fromReviewToString(reviews: List<Review>): String {
        return json.encodeToString(reviews)
    }

    @TypeConverter
    fun fromStringToReview(reviews: String): List<Review> {
        return json.decodeFromString(reviews)
    }

    @TypeConverter
    fun fromDateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromLongToDate(date: Long): Date {
        return Date(date)
    }


    @TypeConverter
    fun fromBookedRoomToString(bookedRooms: Map<String, List<Int>>): String {
        return json.encodeToString(bookedRooms)
    }

    @TypeConverter
    fun fromStringToBookedRooms(bookedRooms: String): Map<String, List<Int>> {
        return json.decodeFromString(bookedRooms)
    }

    @TypeConverter
    fun fromPriceToString(
        prices: Map<String, Map<String, Map<String, Double>>>
    ): String {
        return json.encodeToString(prices)
    }

    @TypeConverter
    fun fromStringToPrice(
        prices: String
    ): Map<String, Map<String, Map<String, Double>>> {
        return json.decodeFromString(prices)
    }
}

