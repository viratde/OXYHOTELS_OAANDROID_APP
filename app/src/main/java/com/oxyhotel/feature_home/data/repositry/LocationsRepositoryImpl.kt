package com.oxyhotel.feature_home.data.repositry

import com.oxyhotel.feature_home.data.data_source.HotelDao
import com.oxyhotel.feature_home.domain.model.Location
import com.oxyhotel.feature_home.domain.repositry.LocationsRepository
import kotlinx.coroutines.flow.Flow

class LocationsRepositoryImpl(
    private val hotelDao: HotelDao
) : LocationsRepository {

    override suspend fun addAndUpdateLocations(locations: List<Location>) {
        hotelDao.clearLocations()
        hotelDao.addLocations(locations)
    }

    override suspend fun getLocations(): Flow<List<Location>> {
        return hotelDao.getLocations()
    }
}