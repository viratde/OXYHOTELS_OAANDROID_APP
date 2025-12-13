package com.oxyhotel.feature_home.presenatation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.presenatation.HotelDetailsRoute
import com.oxyhotel.feature_home.presenatation.composables.HeaderWithBackButton
import com.oxyhotel.feature_home.presenatation.composables.HotelWishShow
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel

@Composable
fun WishlistHotelScreen(
    navController: NavController,
    homeStackViewModel: HomeStackViewModel
) {
    val state = homeStackViewModel.state.collectAsState()

    val wishlistHotels = state.value.hotelsData.filter {
        it.isWishlist
    }

    Screen(
        isScrollable = true,
        verticalArrangement = Arrangement.Top,
        padding = 0
    ) {

        HeaderWithBackButton(
            text = "My Bookmark",
            modifier = Modifier.padding(vertical = 15.dp)
        ) {
            navController.popBackStack()
        }

        if (wishlistHotels.isEmpty()) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "No Wishlist Hotels",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

            }


        }

        wishlistHotels.chunked(2).forEach {
            val first = it.getOrNull(0)
            val second = it.getOrNull(1)
            Row(modifier = Modifier.fillMaxWidth()) {
                first?.let { hotel ->
                    HotelWishShow(
                        hotelName = hotel.hotelName,
                        hotelAddress = hotel.hotelAddress,
                        hotelPrice = hotel.minPrice,
                        hotelRating = hotel.rating,
                        hotelNoOfRatings = hotel.noOfRatings,
                        hotelImageUri = hotel.imageData.values.flatten().firstOrNull() ?: "",
                        isWishlist = hotel.isWishlist,
                        onWishlist = { value ->
                            homeStackViewModel.updateBookMark(hotel._id, value)
                        }
                    ) {
                        navController.navigate("${HotelDetailsRoute.route}/id=${hotel._id}")
                    }
                }
                second?.let { hotel ->
                    HotelWishShow(
                        hotelName = hotel.hotelName,
                        hotelAddress = hotel.hotelAddress,
                        hotelPrice = hotel.minPrice,
                        hotelRating = hotel.rating,
                        hotelNoOfRatings = hotel.noOfRatings,
                        hotelImageUri = hotel.imageData.values.flatten().firstOrNull() ?: "",
                        isWishlist = hotel.isWishlist,
                        onWishlist = { value ->
                            homeStackViewModel.updateBookMark(hotel._id, value)
                        }
                    ) {
                        navController.navigate("${HotelDetailsRoute.route}/id=${hotel._id}")
                    }
                }
            }
        }

    }
}