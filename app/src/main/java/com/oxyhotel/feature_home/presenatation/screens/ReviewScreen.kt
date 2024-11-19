package com.oxyhotel.feature_home.presenatation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.presenatation.composables.HeaderWithBackButton
import com.oxyhotel.feature_home.presenatation.composables.HotelShowSmallH
import com.oxyhotel.feature_home.presenatation.composables.RatingLevelInput
import com.oxyhotel.feature_home.presenatation.viewmodels.ReviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ReviewScreen(
    hotelStorage: HotelStorage,
    reviewViewModel: ReviewViewModel,
    bookingId: String,
    navController: NavController
) {

    val state = reviewViewModel.state.collectAsState()
    val life = LocalLifecycleOwner.current
    val context = LocalContext.current

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        if (msg == "Rated Successfully") {
            navController.popBackStack()
        }
    }
    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            reviewViewModel.clearMessage()
        }
    }

    Screen(
        padding = 0,
        verticalArrangement = Arrangement.Top,
        isScrollable = false
    ) {

        HeaderWithBackButton(
            text = "Rating",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            navController.popBackStack()
        }


        Text(
            text = hotelStorage.hotelName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        HotelShowSmallH(
            hotelName = hotelStorage.hotelName,
            hotelAddress = hotelStorage.hotelAddress,
            hotelPrice = hotelStorage.minPrice,
            hotelRating = 4.4f,
            hotelNoOfRatings = 2231,
            hotelImageUri = hotelStorage.imageData[hotelStorage.imageData.keys.toMutableList()[0]]?.get(
                0
            ) ?: "",
            isWishlist = hotelStorage.isWishlist
        )


        Text(
            text = "Please give your rating & review",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 17.sp
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )

        RatingLevelInput(
            level = state.value.ratingLevel,
            onLevelChange = { reviewViewModel.updateRatingLevel(it) })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {

            BasicTextField(
                value = state.value.ratingNote,
                onValueChange = {
                    reviewViewModel.updateRatingNote(it)
                },
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 15.sp
                ),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                cursorBrush = Brush.linearGradient(listOf(Color.White, Color.White))
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.onBackground
                ),
                contentPadding = PaddingValues(horizontal = 35.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp
                    )
                )
            }

            Button(
                onClick = {
                    life.lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            reviewViewModel.reviewHotel(
                                hotelStorage = hotelStorage,
                                bookingId = bookingId
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                contentPadding = PaddingValues(horizontal = 35.dp, vertical = 10.dp)
            ) {

                if (state.value.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(23.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                } else {
                    Text(
                        text = "Rate",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp
                        )
                    )
                }

            }

        }

    }

}