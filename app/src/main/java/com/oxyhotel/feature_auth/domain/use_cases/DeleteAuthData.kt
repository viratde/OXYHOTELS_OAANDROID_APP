package com.oxyhotel.feature_auth.domain.use_cases

import com.oxyhotel.feature_auth.domain.repository.AuthDataRepository


class DeleteAuthData (
    private val authDataRepository: AuthDataRepository
    ){
    suspend operator fun invoke (){
        return authDataRepository.deleteData()
    }
}