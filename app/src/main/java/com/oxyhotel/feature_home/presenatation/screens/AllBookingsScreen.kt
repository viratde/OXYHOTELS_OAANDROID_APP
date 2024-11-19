package com.oxyhotel.feature_home.presenatation.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.presenatation.BookingDetailsScreenRoute
import com.oxyhotel.feature_home.presenatation.HomeScreenRoute
import com.oxyhotel.feature_home.presenatation.ReviewScreenRoute
import com.oxyhotel.feature_home.presenatation.composables.Booking
import com.oxyhotel.feature_home.presenatation.composables.HeaderWithBackButton
import com.oxyhotel.feature_home.presenatation.composables.HotelChipsSelector
import com.oxyhotel.feature_home.presenatation.viewmodels.AllBookingsViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBookingsScreen(
    allBookingsViewModel: AllBookingsViewModel,
    navController: NavController,
    homeStackViewModel: HomeStackViewModel
) {

    val state = allBookingsViewModel.state.collectAsState()
    val hState = homeStackViewModel.state.collectAsState()

    val isCancelling: MutableState<String?> = rememberSaveable() {
        mutableStateOf(null)
    }
    val context = LocalContext.current
    val life = LocalLifecycleOwner.current
    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            allBookingsViewModel.clearMessage()
        }
    }

    Screen(
        isScrollable = true,
        verticalArrangement = Arrangement.Top,
        padding = 0
    ) {

        if (isCancelling.value != null) {
            AlertDialog(
                onDismissRequest = { isCancelling.value = null },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.onBackground)
                            .padding(vertical = 20.dp, horizontal = 15.dp),
                    ) {
                        Text(
                            text = "Cancel Booking",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 20.dp)
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Are you sure want to cancel your hotel booking?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.background)
                                    .clickable(onClick = { isCancelling.value = null })
                                    .padding(horizontal = 23.dp, vertical = 12.dp)
                                    .animateContentSize()
                            ) {
                                Text(
                                    text = "Back",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )

                            }

                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .clickable {
                                        val bookingId = isCancelling.value
                                        if (bookingId != null) {
                                            life.lifecycleScope.launch {
                                                withContext(Dispatchers.IO) {
                                                    allBookingsViewModel.cancelBooking(bookingId = bookingId)
                                                    isCancelling.value = null
                                                }
                                            }
                                        }
                                    }
                                    .padding(horizontal = 23.dp, vertical = 12.dp)
                                    .animateContentSize()
                            ) {

                                if (state.value.cancellingId == isCancelling.value) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        color = MaterialTheme.colorScheme.background
                                    )
                                } else {
                                    Text(
                                        text = "Cancel",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.background
                                        )
                                    )
                                }

                            }

                        }
                    }
                }
            }
        }

        HeaderWithBackButton(
            text = "My Booking",
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            navController.navigate(HomeScreenRoute.route)
        }

        HotelChipsSelector(
            options = allBookingsViewModel.options,
            selectedValue = state.value.filteredBy,
            onSelect = {
                allBookingsViewModel.setFilter(it)
            }
        )

        val filteredBookings = state.value.bookings.filter {
            when (state.value.filteredBy) {
                "Upcoming" -> !it.isCancelled && !it.hasNotShown && !it.hasCheckedOut && !it.hasCheckedIn
                "Completed" -> it.hasCheckedOut
                "Ongoing" -> it.hasCheckedIn && !it.hasCheckedOut
                "NoShow" -> it.hasNotShown
                else -> it.isCancelled
            }
        }

        filteredBookings.map { booking ->

            val hotelStorage = hState.value.hotelsData.find {
                it._id == booking.hotelId
            } ?: return@map

            Booking(
                booking = booking,
                hotelStorage = hotelStorage,
                isCancelling = state.value.cancellingId == booking.bookingId,
                onCancel = {
                    if (state.value.cancellingId != booking.bookingId) {
                        isCancelling.value = it
                    }
                },
                onClick = {
                    navController.navigate(BookingDetailsScreenRoute.route + "/bookingId=${booking._id}")
                }
            ) {
                navController.navigate(ReviewScreenRoute.route + "/bookingId=${booking._id}")
            }
        }

        if (filteredBookings.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No Bookings",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}