package com.oxyhotel.feature_home.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.presentation.auth.states.AuthResponse
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.presenatation.states.RatingState
import com.oxyhotel.feature_home.presenatation.utils.RatingLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(RatingState())
    val state = _state

    fun updateRatingLevel(ratingLevel: RatingLevel) {
        _state.update {
            state.value.copy(
                ratingLevel = ratingLevel
            )
        }
    }

    fun updateRatingNote(ratingNote: String) {
        _state.update {
            state.value.copy(
                ratingNote = ratingNote
            )
        }
    }

    suspend fun reviewHotel(hotelStorage: HotelStorage, bookingId: String) {
        if (state.value.isLoading) {
            return
        }

        _state.update {
            state.value.copy(
                isLoading = true
            )
        }

        if (state.value.ratingNote.isEmpty()) {
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Please Enter Correct Review Note"
                )
            }
            return
        }

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {
            val token = authUseCases.getAuthData()
            val jsonObject = JSONObject()
            jsonObject.put("hotelId", hotelStorage._id)
            jsonObject.put("ratingNote", state.value.ratingNote)
            jsonObject.put("ratingValue", state.value.ratingLevel.ordinal)
            jsonObject.put("bookingId", bookingId)
            val httpResponse = client.post(Constant.postReviewRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer ${token?.authToken}")
                    setBody(jsonObject.toString())
                }
            }
            if (httpResponse.status != HttpStatusCode.OK) {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }
            val rData = Gson().fromJson(httpResponse.body<String>(), AuthResponse::class.java)
            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = rData.message
                    )
                }
                return
            }
//            val hotelStorage = Gson()

            println(rData.data)
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Rated Successfully"
                )
            }
        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = err.message ?: "Please try after some time"
                )
            }
        }
    }

    fun clearMessage() {
        _state.update {
            state.value.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }

}