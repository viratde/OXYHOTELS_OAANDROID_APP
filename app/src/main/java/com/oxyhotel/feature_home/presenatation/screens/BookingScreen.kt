package com.oxyhotel.feature_home.presenatation.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.oxyhotel.R
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.presenatation.AllBookingsScreenRoute
import com.oxyhotel.feature_home.presenatation.composables.CustomBookRoom
import com.oxyhotel.feature_home.presenatation.viewmodels.BookingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    hotelStorage: HotelStorage,
    navController: NavController,
    bookingViewModel: BookingViewModel,
) {

    val life = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val currentTimeInMillis = remember {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        calendar.timeInMillis
    }


    val state = bookingViewModel.state.collectAsState()
    val context = LocalContext.current

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        if (msg == "Booking Created Successfully") {
            navController.navigate(AllBookingsScreenRoute.route) {
                launchSingleTop = true
                popUpTo(navController.graph.startDestinationId)
            }
        }
    }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            bookingViewModel.clearMessage()
        }
    }

    val dateState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = remember {
            Calendar.getInstance().timeInMillis
        },
        initialSelectedEndDateMillis = remember {
            val calendar = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 1)
            }
            calendar.timeInMillis
        },
        selectableDates = object : SelectableDates {

            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis > currentTimeInMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return true
            }

        }
    )
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    LaunchedEffect(key1 = dateState.selectedEndDateMillis) {
        bookingViewModel.updateCheckInTime(dateState.selectedStartDateMillis?.let { Date(it) }
            ?.let { dateFormat.format(it) } ?: "")
    }
    LaunchedEffect(key1 = dateState.selectedEndDateMillis) {
        bookingViewModel.updateCheckOutTime(dateState.selectedEndDateMillis?.let { Date(it) }
            ?.let { dateFormat.format(it) } ?: "")
    }

    Screen(
        isScrollable = true,
        verticalArrangement = Arrangement.Top,
        padding = 0,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        ) {

            DateRangePicker(
                state = dateState,
                headline = {},
                showModeToggle = false,
                title = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .offset { IntOffset(0, -210) },
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = "Check In", style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 17.sp, color = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Text(
                        text = state.value.checkInTime,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(130.dp)
                            .padding(top = 15.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.onBackground)
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                    )

                }


                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = "Check Out", style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 17.sp, color = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Text(
                        text = state.value.checkOutTime,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(130.dp)
                            .padding(top = 15.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.onBackground)
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                    )

                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .animateContentSize()
        ) {

            Text(
                text = "Rooms and Guests", style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary
                ), modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                state.value.rooms.map {

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 20.dp),
                    ) {

                        CustomBookRoom(
                            mainText = it.key,
                            onClick = { action ->
                                bookingViewModel.updateRoomsValue(
                                    it.key,
                                    action
                                )
                            },
                            value = it.value.size.toString()
                        )

                        it.value.mapIndexed { index, value ->
                            CustomBookRoom(
                                mainText = "Guests in Room${index + 1}",
                                onClick = { action ->
                                    bookingViewModel.updateGuest(
                                        it.key,
                                        action,
                                        index
                                    )
                                },
                                value = value.toString()
                            )
                        }
                    }

                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = if (state.value.amount != null) "₹${state.value.amount}" else "₹0",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Row(
                modifier = Modifier
                    .width(130.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable(onClick = {
                        life.lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                bookingViewModel.createBooking(hotelStorage._id)
                            }
                        }
                    })
                    .padding(vertical = 13.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (state.value.isCalculating) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground,
                        strokeWidth = 1.dp,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )

                } else {
                    Text(
                        text = "Book",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }
    }
}
