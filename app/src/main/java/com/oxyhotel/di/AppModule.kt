package com.oxyhotel.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.oxyhotel.feature_auth.data.repository.AuthDataRepositoryImpl
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.domain.use_cases.DeleteAuthData
import com.oxyhotel.feature_auth.domain.use_cases.GetAuthData
import com.oxyhotel.feature_auth.domain.use_cases.SetAuthData
import com.oxyhotel.feature_home.data.data_source.HotelDatabase
import com.oxyhotel.feature_home.data.repositry.BookingRepositoryImpl
import com.oxyhotel.feature_home.data.repositry.HotelRepositoryImpl
import com.oxyhotel.feature_home.data.repositry.LocationsRepositoryImpl
import com.oxyhotel.feature_home.domain.repositry.BookingRepository
import com.oxyhotel.feature_home.domain.repositry.HotelRepository
import com.oxyhotel.feature_home.domain.repositry.LocationsRepository
import com.oxyhotel.feature_home.domain.use_cases.AddBooking
import com.oxyhotel.feature_home.domain.use_cases.AddBookings
import com.oxyhotel.feature_home.domain.use_cases.AddHotel
import com.oxyhotel.feature_home.domain.use_cases.AddHotels
import com.oxyhotel.feature_home.domain.use_cases.AddLocationsUseCases
import com.oxyhotel.feature_home.domain.use_cases.BookMarkHotel
import com.oxyhotel.feature_home.domain.use_cases.BookingUseCases
import com.oxyhotel.feature_home.domain.use_cases.ClearBookings
import com.oxyhotel.feature_home.domain.use_cases.ClearHotels
import com.oxyhotel.feature_home.domain.use_cases.GetBookingById
import com.oxyhotel.feature_home.domain.use_cases.GetBookings
import com.oxyhotel.feature_home.domain.use_cases.GetHotel
import com.oxyhotel.feature_home.domain.use_cases.GetHotelById
import com.oxyhotel.feature_home.domain.use_cases.GetLocationsUseCases
import com.oxyhotel.feature_home.domain.use_cases.HotelUseCases
import com.oxyhotel.feature_home.domain.use_cases.LocationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("AuthUserPreference", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesAuthUseCases(authSharedPreferences: SharedPreferences): AuthUseCases {

        val authDataRepository = AuthDataRepositoryImpl(
            sharedPreferences = authSharedPreferences
        )

        return AuthUseCases(
            getAuthData = GetAuthData(authDataRepository),
            setAuthData = SetAuthData(authDataRepository),
            deleteAuthData = DeleteAuthData(authDataRepository)
        )
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(app: Application): HotelDatabase {
        return Room.databaseBuilder(
            app, HotelDatabase::class.java, HotelDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesHotelRepository(db: HotelDatabase): HotelRepository {
        return HotelRepositoryImpl(db.hotelDao)
    }


    @Provides
    @Singleton
    fun providesBookingRepository(db: HotelDatabase): BookingRepository {
        return BookingRepositoryImpl(db.hotelDao)
    }


    @Provides
    @Singleton
    fun providesHotelUseCases(hotelRepository: HotelRepository): HotelUseCases {
        return HotelUseCases(
            addHotel = AddHotel(hotelRepository),
            getHotel = GetHotel(hotelRepository),
            addHotels = AddHotels(hotelRepository),
            clearHotels = ClearHotels(hotelRepository),
            getHotelById = GetHotelById(hotelRepository),
            bookMarkHotel = BookMarkHotel(hotelRepository)
        )
    }

    @Provides
    @Singleton
    fun providesBookingUseCases(bookingRepository: BookingRepository): BookingUseCases {
        return BookingUseCases(
            addBooking = AddBooking(bookingRepository = bookingRepository),
            addBookings = AddBookings(bookingRepository = bookingRepository),
            getBooking = GetBookings(bookingRepository = bookingRepository),
            getBookingById = GetBookingById(bookingRepository = bookingRepository),
            clearBookings = ClearBookings(bookingRepository = bookingRepository)
        )
    }

    @Provides
    @Singleton
    fun providesLocationRepository(db: HotelDatabase): LocationsRepository {
        return LocationsRepositoryImpl(
            hotelDao = db.hotelDao
        )
    }

    @Provides
    @Singleton
    fun providesLocationUseCases(locationsRepository: LocationsRepository): LocationUseCases {
        return LocationUseCases(
            addLocationsUseCases = AddLocationsUseCases(
                locationsRepository = locationsRepository
            ), getLocationsUseCases = GetLocationsUseCases(
                locationsRepository = locationsRepository
            )
        )
    }

}