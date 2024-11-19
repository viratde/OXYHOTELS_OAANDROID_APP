package com.oxyhotel.feature_home.domain.use_cases

import com.oxyhotel.feature_home.domain.model.Location
import com.oxyhotel.feature_home.domain.repositry.LocationsRepository

class AddLocationsUseCases(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(locations: List<Location>) {
        locationsRepository.addAndUpdateLocations(locations)
    }
}