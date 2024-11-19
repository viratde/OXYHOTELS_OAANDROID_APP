package com.oxyhotel.feature_home.presenatation.states

import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse

data class ProfileState(
    val isError: Boolean = false,
    val errorMessage: String = "",
    val userData: SignupResponse? = null,
    val isLoading: Boolean = false,
)




