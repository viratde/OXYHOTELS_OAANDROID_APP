package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.Location
import com.oxyhotel.feature_home.domain.repositry.LocationsRepository
import kotlinx.coroutines.flow.Flow

class GetLocationsUseCases(
    private val locationsRepository: LocationsRepository
) {

    suspend operator fun invoke(): Flow<List<Location>> {
        return locationsRepository.getLocations()
    }

}