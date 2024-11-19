package com.oxyhotel.feature_home.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.model.Location
import kotlinx.coroutines.flow.Flow


@Dao
interface HotelDao {

    @Query("SELECT * from locations")
    fun getLocations(): Flow<List<Location>>

    @Query("DELETE from locations")
    fun clearLocations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocations(locations: List<Location>)

    @Query("SELECT * from hotelStorage")
    fun getHotels(): Flow<List<HotelStorage>>

    @Query("SELECT * from hotelStorage where hotelId=:hotelId")
    fun getHotelById(hotelId: String): HotelStorage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHotel(hotelStorage: HotelStorage)

    @Delete
    fun deleteHotel(hotelStorage: HotelStorage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHotels(hotelStorages: List<HotelStorage>)

    @Query("Delete from hotelStorage")
    fun clearHotels()

    @Query("UPDATE hotelStorage SET isWishlist=:isWishlist WHERE _id=:hotelId")
    fun bookMarkUpdate(hotelId: String, isWishlist: Boolean)


    @Query("SELECT * from bookings")
    fun getBookings(): Flow<List<BookingStorage>>

    @Query("SELECT * from bookings where bookingId=:bookingId")
    fun getBookingById(bookingId: Int): BookingStorage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBooking(bookingStorage: BookingStorage)

    @Delete
    fun deleteBooking(bookingStorage: BookingStorage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookings(hotelStorages: List<BookingStorage>)

    @Query("Delete from bookings")
    fun clearBookings()

}