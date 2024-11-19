package com.oxyhotel.feature_home.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.presentation.auth.states.AuthResponse
import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse
import com.oxyhotel.feature_home.presenatation.states.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(ProfileState())
    val state = _state

    init {
        viewModelScope.launch {
            val data = authUseCases.getAuthData()
            _state.value = state.value.copy(
                userData = data
            )
        }
    }

    fun clearMessage() {
        _state.value = state.value.copy(
            isError = false,
            errorMessage = ""
        )
    }

    suspend fun updateData(
        name: String,
        email: String,
        dob: String,
        phone: String
    ) {

        println(phone)
        if (state.value.isLoading) {
            return
        }
        _state.value = state.value.copy(
            isLoading = true
        )

        if (name.isEmpty()) {
            _state.value = state.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Please Enter Correct Name"
            )
            return
        }
        if (email.isEmpty() || !email.contains("@")) {
            _state.value = state.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Please Enter Correct Email"
            )
            return
        }

        if (dob.isEmpty() || !dob.contains("-") || dob.length != 10) {
            _state.value = state.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Please Enter Correct Dob"
            )
            return
        }

        if (phone.isEmpty() || phone.toDoubleOrNull() == null) {
            _state.value = state.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Please Enter Correct Phone Number"
            )
            return
        }


        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }
        val jsonObject = JSONObject()

        try {

            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("phone", phone)
            jsonObject.put("dob", dob)

            val response = client.post(Constant.updateProfileRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer ${state.value.userData?.authToken}")
                }
                setBody(jsonObject.toString())
            }.body<String>()

            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = rData.message
                )
                return
            }

            val data = SignupResponse(
                authToken = state.value.userData?.authToken,
                dob = dob,
                name = name,
                email = email,
                phoneNumber = phone,
                gender = state.value.userData?.gender
            )
            withContext(Dispatchers.IO) {
                authUseCases.setAuthData(data)
                _state.value = state.value.copy(
                    userData = data
                )
            }

            _state.value = state.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = "Updated Successfully"
            )

        } catch (err: Exception) {
            err.printStackTrace()
            _state.value = state.value.copy(
                isLoading = false,
                isError = true,
                errorMessage = err.message.toString()
            )
        }
    }

}