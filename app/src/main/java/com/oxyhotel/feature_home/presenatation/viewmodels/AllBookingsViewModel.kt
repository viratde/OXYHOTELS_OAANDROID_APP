package com.oxyhotel.feature_home.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.presentation.auth.states.AuthResponse
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.use_cases.BookingUseCases
import com.oxyhotel.feature_home.presenatation.states.AllBookingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllBookingsViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val bookingUseCases: BookingUseCases,
    private val client: HttpClient
) : ViewModel() {

    val options = listOf(
        "Upcoming",
        "Ongoing",
        "Completed",
        "NoShow",
        "Cancelled",
    )

    private val _state = MutableStateFlow(AllBookingsState())
    val state = _state

    init {
        bookingUseCases
            .getBooking()
            .onEach {
                _state.value = state.value.copy(
                    bookings = it
                )
            }
            .launchIn(viewModelScope)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadRemoteData()
            }
        }
    }


    private suspend fun loadRemoteData() {
        _state.value = state.value.copy(
            isRemoteDataLoading = true
        )

        try {
            val token = authUseCases.getAuthData()?.authToken

            val response = client.post(Constant.getAllBookings) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
            }.body<String>()


            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    isRemoteDataLoading = false,
                    isError = true,
                    errorMessage = rData.message
                )
                return
            }
            val typeToken = object : TypeToken<MutableList<BookingStorage>>() {}.type
            val serverBookings = Gson().fromJson<MutableList<BookingStorage>>(rData.data, typeToken)

            bookingUseCases.clearBookings()
            bookingUseCases.addBookings(serverBookings)

            _state.value = state.value.copy(
                isRemoteDataLoading = false,
                isRemoteDataLoaded = true,
                isError = true,
                errorMessage = "Booking Synced Successfully"
            )

        } catch (err: Exception) {
            err.printStackTrace()
            _state.value = state.value.copy(
                isRemoteDataLoading = true,
                isError = true,
                errorMessage = err.message.toString()
            )
        }
    }

    suspend fun cancelBooking(
        bookingId: String?
    ) {

        val bookingStorage = state.value.bookings.find {
            it.bookingId == bookingId
        } ?: return

        if (state.value.cancellingId == bookingId) {
            return
        }

        _state.update {
            state.value.copy(
                cancellingId = bookingId
            )
        }

        try {

            val token = authUseCases.getAuthData()?.authToken

            val response = client.post(Constant.cancelBookingRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("bookingId", bookingId)
                }
            }.body<String>()

            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    cancellingId = null,
                    isError = true,
                    errorMessage = rData.message
                )
                return
            }
            bookingUseCases.addBookings(
                listOf(
                    bookingStorage.copy(
                        isCancelled = true
                    )
                )
            )
            _state.value = state.value.copy(
                cancellingId = null,
                isError = true,
                errorMessage = "Booking Cancelled Successfully"
            )

        } catch (err: Exception) {
            _state.value = state.value.copy(
                cancellingId = null,
                isError = true,
                errorMessage = err.message ?: "Please try after some time."
            )
        }

    }

    fun clearMessage() {
        _state.value = state.value.copy(
            isError = false,
            errorMessage = ""
        )
    }

    fun setFilter(filter: String) {
        _state.value = state.value.copy(
            filteredBy = filter
        )
    }
}

