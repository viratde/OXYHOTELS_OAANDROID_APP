package com.oxyhotel.feature_home.domain.repositry

import com.oxyhotel.feature_home.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {

    suspend fun addAndUpdateLocations(locations: List<Location>)

    suspend fun getLocations(): Flow<List<Location>>
}