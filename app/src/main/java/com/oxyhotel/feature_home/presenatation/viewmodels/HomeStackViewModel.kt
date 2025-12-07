package com.oxyhotel.feature_home.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.presentation.auth.states.AuthResponse
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.use_cases.HotelUseCases
import com.oxyhotel.feature_home.domain.use_cases.LocationUseCases
import com.oxyhotel.feature_home.presenatation.states.HomeStackState
import com.oxyhotel.feature_home.presenatation.utils.HotelAndLocationData
import com.oxyhotel.feature_home.presenatation.utils.HotelStorageFilter
import com.oxyhotel.feature_home.presenatation.utils.SelectedCity
import com.oxyhotel.feature_home.presenatation.utils.UserLocation
import com.oxyhotel.feature_home.presenatation.utils.distanceBetweenLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
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
class HomeStackViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val hotelUseCases: HotelUseCases,
    private val locationUseCases: LocationUseCases,
    private val client: HttpClient
) : ViewModel() {

    private val _state = MutableStateFlow(HomeStackState())
    val state = _state

    init {

        hotelUseCases.getHotel().onEach { hot ->
            _state.update {
                state.value.copy(
                    hotelsData = hot, showHotels = updateHotels(
                        hot, it.userLocation, it.selectedCity
                    )
                )
            }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
            locationUseCases.getLocationsUseCases().onEach { locations ->
                _state.update {
                    state.value.copy(
                        locations = locations
                    )
                }
            }.launchIn(viewModelScope)
        }
        viewModelScope.launch {
            getRemoteHotels()
        }

    }

    private suspend fun getRemoteHotels() {

        _state.value = state.value.copy(isRemoteHotelLoading = true)

        try {
            val token = authUseCases.getAuthData()?.authToken!!

            val response = client.post(Constant.getHotelRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
            }.body<String>()

            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    isRemoteHotelLoading = false, isError = true, errorMessage = rData.message
                )
                return
            }


            val typeToken = object : TypeToken<HotelAndLocationData>() {}.type
            val serverHotels = Gson().fromJson<HotelAndLocationData>(rData.data, typeToken)
            withContext(Dispatchers.IO) {
                hotelUseCases.clearHotels()
                locationUseCases.addLocationsUseCases(serverHotels.locations)
                if (serverHotels != null) {
                    hotelUseCases.addHotels(serverHotels.hotels)
                }
            }

            _state.value = state.value.copy(
                isRemoteHotelLoading = false,
                isRemoteHotelLoaded = true,
                isError = true,
                errorMessage = "Hotels Synced Successfully."
            )

            return
        } catch (err: Exception) {
            err.printStackTrace()
            _state.value = state.value.copy(
                isRemoteHotelLoading = false,
                isError = true,
                errorMessage = "Please try after some time."
            )
        }

    }

    fun updatePermissionStatus(status: Boolean) {
        _state.update {
            state.value.copy(
                hasLocationPermission = status
            )
        }
    }


    fun updateSelectedCity(city: SelectedCity) {

        _state.update {
            state.value.copy(
                selectedCity = city, showHotels = updateHotels(
                    it.hotelsData, it.userLocation, city
                )
            )
        }

    }

    fun updateUserLocation(userLocation: UserLocation) {
        _state.update {
            state.value.copy(
                userLocation = userLocation, showHotels = updateHotels(
                    it.hotelsData, userLocation, it.selectedCity
                )
            )
        }

    }


    private fun updateHotels(
        hotelsData: List<HotelStorage>, userLocation: UserLocation?, selectedCity: SelectedCity?
    ): List<HotelStorage> {

        when (selectedCity) {
            is SelectedCity.All -> {
                return hotelsData
            }

            is SelectedCity.NearBy -> {
                return if (userLocation == null) {
                    hotelsData
                } else {
                    hotelsData.map {
                        HotelStorageFilter(
                            hotel = it, distance = distanceBetweenLocations(
                                userLocation, UserLocation(
                                    it.latitude, it.longitude
                                )
                            )
                        )
                    }.filter { it.distance < 50 }.sortedBy { hot ->
                        println("${hot.hotel.hotelName} ${hot.distance}")
                        hot.distance
                    }.map { it.hotel }
                }
            }

            is SelectedCity.SelectedLocation -> {
                return hotelsData.map { hot ->
                    HotelStorageFilter(
                        hotel = hot, distance = distanceBetweenLocations(
                            UserLocation(
                                latitude = selectedCity.location.latitude,
                                longitude = selectedCity.location.longitude
                            ), UserLocation(
                                hot.latitude, hot.longitude
                            )
                        )
                    )
                }.filter { it.distance < 50 }.sortedBy { hot ->
                    hot.distance
                }.map { it.hotel }
            }

            null -> {
                return hotelsData
            }
        }

    }

    fun updateBookMark(hotelId: String, isWishlist: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                hotelUseCases.bookMarkHotel(hotelId = hotelId, isWishlist = isWishlist)
            }
        }
    }

    fun clearMessage() {
        _state.value = state.value.copy(
            isError = false, errorMessage = ""
        )
    }
}