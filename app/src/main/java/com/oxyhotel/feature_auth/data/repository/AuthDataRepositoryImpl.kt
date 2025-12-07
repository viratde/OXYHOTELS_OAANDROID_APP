package com.oxyhotel.feature_auth.data.repository

import android.content.SharedPreferences
import com.oxyhotel.feature_auth.domain.repository.AuthDataRepository
import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val json: Json
) : AuthDataRepository {

    override suspend fun getData(): SignupResponse? {
        val user = sharedPreferences.getString("authUserData", null)
        return user?.let { json.decodeFromString(SignupResponse.serializer(), user) }
    }

    override suspend fun setData(authDataModel: SignupResponse) {
        sharedPreferences.edit().putString(
            "authUserData",
            json.encodeToString(authDataModel)
        ).apply()
    }

    override suspend fun deleteData() {
        sharedPreferences.edit().remove("authUserData").apply()
    }


}
