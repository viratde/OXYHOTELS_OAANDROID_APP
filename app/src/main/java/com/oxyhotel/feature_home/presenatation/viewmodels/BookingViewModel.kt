package com.oxyhotel.feature_home.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.presentation.auth.states.AuthResponse
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.domain.use_cases.BookingUseCases
import com.oxyhotel.feature_home.presenatation.states.BookingState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class BookingViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val bookingUseCases: BookingUseCases
) : ViewModel() {

    private val calendar: Calendar = Calendar.getInstance().also {
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)
    }

    private lateinit var hotel: HotelStorage

    private val currentTimeInMillis = calendar.timeInMillis
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val _state = MutableStateFlow(
        BookingState(
            checkInTime = dateFormat.format(Date(currentTimeInMillis)),
            checkOutTime = dateFormat.format(Date(currentTimeInMillis + 24L * 60L * 60L * 1000L)),
        )
    )

    val state = _state


    fun updateCheckInTime(checkInTime: String) {
        _state.value = state.value.copy(
            checkInTime = checkInTime,
            amount = calculatePrice(state.value.rooms, state.value.checkOutTime, checkInTime),
        )
    }

    fun setRooms(hotelStorage: HotelStorage) {
        hotel = hotelStorage
        val newRooms: MutableMap<String, List<Int>> = mutableMapOf()

        hotelStorage.roomTypes.keys.forEach { roomType ->
            newRooms[roomType] = listOf(1)
        }
        _state.value = state.value.copy(
            rooms = newRooms,
            amount = hotel.minPrice
        )
    }

    fun updateCheckOutTime(checkOutTime: String) {
        _state.value = state.value.copy(
            checkOutTime = checkOutTime,
            amount = calculatePrice(state.value.rooms, checkOutTime, state.value.checkInTime)
        )
    }

    fun updateRoomsValue(roomType: String, action: Int) {
        _state.update {
            val rooms = state.value.rooms.toMutableMap()
            val guest = rooms[roomType]?.toMutableList()!!
            if (action < 0) {
                if (guest.isNotEmpty()) {
                    guest.removeLast()
                }
            } else {
                guest.add(1)
            }
            rooms[roomType] = guest
            state.value.copy(
                rooms = rooms,
                amount = calculatePrice(rooms, state.value.checkOutTime, state.value.checkInTime)
            )
        }
    }

    private fun calculatePrice(
        rooms: Map<String, List<Int>>,
        checkOut: String,
        checkIn: String
    ): Int {
        val aDates = findAllAccountingDates(checkOut, checkIn)
        return aDates.sumOf { date ->
            rooms.keys.toList().sumOf { roomType ->
                rooms[roomType]?.sumOf { guests ->
                    val value = hotel.prices[roomType]?.get(date)
                        ?.get("pax${if (guests <= 3) guests else 3}Price") ?: hotel.maxPrice
                    value.toInt()
                } ?: 0
            }
        }
    }

    private fun calculateDate(checkOut: String, checkIn: String): Int {
        try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            println("$checkIn $checkOut")
            val checkOutTime = (dateFormat.parse(checkOut)?.time ?: 0)
            val checkInTime = (dateFormat.parse(checkIn)?.time ?: 0)
            println("$checkOutTime $checkInTime")
            val oneDayTime = (24L * 60L * 60L * 1000L)
            if (checkOutTime.toInt() == 0) {
                return 0
            }

            return ((checkOutTime - checkInTime) / oneDayTime).toInt()
        } catch (err: Exception) {
            return 0
        }
    }

    private fun findAllAccountingDates(checkOut: String, checkIn: String): List<String> {
        try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val checkOutTime = (dateFormat.parse(checkOut) ?: return listOf())
            val checkInTime = (dateFormat.parse(checkIn) ?: return listOf())

            val noOfDates =
                ceil(((checkOutTime.time - checkInTime.time) / ((24 * 60 * 60 * 1000))).toDouble()).toInt()
            val aList: MutableList<String> = mutableListOf(checkIn)

            for (i in 1 until noOfDates) {
                aList.add(
                    dateFormat.format(addDaysToDate(checkInTime, i))
                )
            }

            return aList
        } catch (err: Exception) {
            return emptyList()
        }
    }

    private fun addDaysToDate(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.time
    }

    fun updateGuest(roomType: String, action: Int, index: Int) {
        _state.update {
            val rooms = state.value.rooms.toMutableMap()
            val guest = rooms[roomType]?.toMutableList()!!
            if (action < 0) {
                guest[index] = guest[index] - 1
                if (guest[index] == 0) {
                    guest.removeAt(index)
                }
            } else {
                if (guest[index] <= 2) {
                    guest[index] = guest[index] + 1
                }
            }
            rooms[roomType] = guest
            state.value.copy(
                rooms = rooms,
                amount = calculatePrice(rooms, state.value.checkOutTime, state.value.checkInTime)
            )
        }
    }

    suspend fun checkStatus(hotelId: String) {
        if (state.value.isCalculating) {
            return
        }

        _state.value = state.value.copy(
            isCalculating = true
        )

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }


        try {

            val response = client.post("${Constant.domain}/api/checkStatus") {
                headers {
                    append("Content-Type", "application/json")
                }
                parameter("hotelId", hotelId)
                setBody(Gson().toJson(state.value))
            }.body<String>()

            client.close()

            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    isCalculating = false,
                    isError = true,
                    errorMessage = rData.message
                )
            }

            _state.value = state.value.copy(
                isCalculating = false,
                amount = rData.data?.toIntOrNull()
            )
        } catch (err: Exception) {
            _state.value = state.value.copy(
                isCalculating = false,
                isError = true,
                errorMessage = err.message.toString()
            )
        }
    }


    suspend fun createBooking(
        hotelId: String
    ) {
        if (state.value.isCalculating) {
            return
        }

        _state.value = state.value.copy(
            isCalculating = true
        )

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }


        try {

            val token = authUseCases.getAuthData()?.authToken
            val response = client.post(Constant.createBookingRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                parameter("hotelId", hotelId)
                setBody(Gson().toJson(state.value))
            }.body<String>()


            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    isCalculating = false,
                    isError = true,
                    errorMessage = rData.message
                )
                return
            }

            val serverBooking = Gson().fromJson(rData.data, BookingStorage::class.java)
            bookingUseCases.addBooking(serverBooking)
            _state.value = state.value.copy(
                isCalculating = false,
                isError = true,
                errorMessage = "Booking Created Successfully"
            )
        } catch (err: Exception) {
            _state.value = state.value.copy(
                isCalculating = false,
                isError = true,
                errorMessage = err.message.toString()
            )
        }
    }

    fun clearMessage() {
        _state.value = state.value.copy(
            isError = false,
            errorMessage = ""
        )
    }

}