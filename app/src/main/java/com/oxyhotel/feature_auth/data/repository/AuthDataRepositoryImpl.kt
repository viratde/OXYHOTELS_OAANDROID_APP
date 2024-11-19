package com.oxyhotel.feature_auth.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.oxyhotel.feature_auth.domain.repository.AuthDataRepository
import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse

class AuthDataRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AuthDataRepository {

    override suspend fun getData(): SignupResponse? {
        val user = sharedPreferences.getString("authUserData", null)
        return user?.let { Gson().fromJson(user, SignupResponse::class.java) }
    }

    override suspend fun setData(authDataModel: SignupResponse) {
        sharedPreferences.edit().putString(
            "authUserData",
            Gson().toJson(authDataModel)
        ).apply()
    }

    override suspend fun deleteData() {
        sharedPreferences.edit().remove("authUserData").apply()
    }


}