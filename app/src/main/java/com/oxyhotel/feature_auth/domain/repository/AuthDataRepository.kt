package com.oxyhotel.feature_auth.domain.repository

import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse


interface AuthDataRepository {

    suspend fun getData(): SignupResponse?

    suspend fun setData(authDataModel: SignupResponse)

    suspend fun deleteData()
}