package com.oxyhotel.feature_home.presenatation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.presenatation.HotelDetailsRoute
import com.oxyhotel.feature_home.presenatation.composables.HeaderWithBackButton
import com.oxyhotel.feature_home.presenatation.composables.HotelSearch
import com.oxyhotel.feature_home.presenatation.composables.HotelWishShow
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel
import kotlin.math.ceil

@Composable
fun SearchHotelScreen(
    navController: NavController,
    homeStackViewModel: HomeStackViewModel
) {

    val state = homeStackViewModel.state.collectAsState()

    var searchString by remember {
        mutableStateOf("")
    }


    val searchedHotels = state.value.hotelsData.filter { hotel ->
        with(hotel) {
            hotelName.lowercase().contains(searchString) ||
                    hotelAddress.lowercase().contains(searchString) ||
                    hotelDescription.lowercase().contains(searchString)
        }
    }

    Screen(
        isScrollable = true,
        verticalArrangement = Arrangement.Top,
        padding = 0
    ) {

        HeaderWithBackButton(
            text = "Search Hotels",
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            navController.popBackStack()
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(MaterialTheme.colorScheme.background),
            color = MaterialTheme.colorScheme.background
        )


        HotelSearch(
            isEnabled = true,
            search = searchString,
            onSearchChange = { searchString = it.lowercase() }
        )

        if (searchedHotels.isEmpty()) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "No Hotels Found",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

            }


        }

        for (i in 0 until ceil(searchedHotels.size.toFloat() / 2.0f).toInt()) {
            Row(modifier = Modifier.fillMaxWidth()) {

                val it = searchedHotels[i * 2]
                HotelWishShow(
                    hotelName = it.hotelName,
                    hotelAddress = it.hotelAddress,
                    hotelPrice = it.minPrice,
                    hotelRating = it.reviews.sumOf { it.ratingLevel } / (if (it.reviews.size > 0) it.reviews.size else 1),
                    hotelNoOfRatings = it.reviews.size,
                    hotelImageUri = it.imageData[it.imageData.keys.toMutableList()[0]]?.get(0)
                        ?: "",
                    isWishlist = it.isWishlist,
                    onWishlist = { value ->
                        homeStackViewModel.updateBookMark(it._id, value)
                    }
                ) {
                    navController.navigate("${HotelDetailsRoute.route}/id=${it._id}")
                }
                if ((i * 2) + 1 < searchedHotels.size) {
                    val it = searchedHotels[(i * 2) + 1]
                    HotelWishShow(
                        hotelName = it.hotelName,
                        hotelAddress = it.hotelAddress,
                        hotelPrice = it.minPrice,
                        hotelRating = it.rating,
                        hotelNoOfRatings = it.noOfRatings,
                        hotelImageUri = it.imageData[it.imageData.keys.toMutableList()[0]]?.get(0)
                            ?: "",
                        isWishlist = it.isWishlist,
                        onWishlist = { value ->
                            homeStackViewModel.updateBookMark(it._id, value)
                        }
                    ) {
                        navController.navigate("${HotelDetailsRoute.route}/id=${it._id}")
                    }
                }
            }
        }
    }
}