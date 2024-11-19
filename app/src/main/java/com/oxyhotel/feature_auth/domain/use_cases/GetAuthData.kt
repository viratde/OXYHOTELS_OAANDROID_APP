package com.oxyhotel.feature_auth.domain.use_cases

import com.oxyhotel.feature_auth.domain.repository.AuthDataRepository
import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse

class GetAuthData(
    private val authDataRepository: AuthDataRepository
) {

    suspend operator fun invoke(): SignupResponse? {
        return authDataRepository.getData()
    }
}