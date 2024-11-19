package com.oxyhotel.feature_home.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.model.Location


@Database(
    entities = [HotelStorage::class, BookingStorage::class, Location::class],
    version = 7,
    exportSchema = false
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class HotelDatabase : RoomDatabase() {
    abstract val hotelDao: HotelDao

    companion object {
        const val DATABASE_NAME = "hotels_db"
    }
}