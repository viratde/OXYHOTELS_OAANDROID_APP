package com.oxyhotel.feature_home.data.data_source

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotel.feature_home.domain.model.ActualRoom
import com.oxyhotel.feature_home.domain.model.Review
import java.util.Date

class TypeConverters {


    @TypeConverter
    fun fromStringToArray(value: String): List<String> {
        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, typeToken)
    }

    @TypeConverter
    fun fromArrayToString(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToMap(value: String): Map<String, List<String>> {
        val typeToken = object : TypeToken<Map<String, List<String>>>() {}.type
        return Gson().fromJson(value, typeToken)
    }

    @TypeConverter
    fun fromMapToString(value: Map<String, List<String>>): String {
        return Gson().toJson(value)
    }


    @TypeConverter
    fun fromRoomTypes(roomTypes: Map<String, Map<String, Any>>): String {
        return Gson().toJson(roomTypes)
    }

    @TypeConverter
    fun toRoomTypes(roomTypes: String): Map<String, Map<String, Any>> {

        val typeToken = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
        return Gson().fromJson(roomTypes, typeToken)
    }

    @TypeConverter
    fun fromFloorData(structure: Map<String, List<ActualRoom>>): String {
        return Gson().toJson(structure)
    }

    @TypeConverter
    fun toFloorData(data: String): Map<String, List<ActualRoom>> {
        val typeToken = object : TypeToken<Map<String, List<ActualRoom>>>() {}.type
        return Gson().fromJson(data, typeToken)
    }


    @TypeConverter
    fun fromReviewToString(reviews: MutableList<Review>): String {
        return Gson().toJson(reviews)
    }

    @TypeConverter
    fun fromStringToReview(reviews: String): MutableList<Review> {
        val typeToken = object : TypeToken<MutableList<Review>>() {}.type
        return Gson().fromJson(reviews, typeToken)
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
        return Gson().toJson(bookedRooms)
    }

    @TypeConverter
    fun fromStringToBookedRooms(bookedRooms: String): Map<String, List<Int>> {
        val typeToken = object : TypeToken<Map<String, List<Int>>>() {}.type
        return Gson().fromJson(bookedRooms, typeToken)
    }

    @TypeConverter
    fun fromPriceToString(
        prices: MutableMap<String, MutableMap<String, MutableMap<String, Number>>>
    ): String {
        return Gson().toJson(prices)
    }

    @TypeConverter
    fun fromStringToPrice(
        prices: String
    ): MutableMap<String, MutableMap<String, MutableMap<String, Number>>> {
        val typeToken = object :
            TypeToken<MutableMap<String, MutableMap<String, MutableMap<String, Number>>>>() {}.type
        return Gson().fromJson(prices, typeToken)
    }
}


