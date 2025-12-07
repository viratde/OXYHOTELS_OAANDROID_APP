package com.oxyhotel.feature_home.presenatation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_home.domain.model.HotelStorage
import com.oxyhotel.feature_home.presenatation.BookingScreenRoute
import com.oxyhotel.feature_home.presenatation.composables.HotelDescription
import com.oxyhotel.feature_home.presenatation.composables.HotelDetailSImageSwiper
import com.oxyhotel.feature_home.presenatation.composables.HotelDetailsDetails
import com.oxyhotel.feature_home.presenatation.composables.HotelDetailsList
import com.oxyhotel.feature_home.presenatation.composables.HotelDetailsReview
import com.oxyhotel.feature_home.presenatation.composables.HotelGalleryPhotos

@Composable
fun HotelDetailsScreen(
    hotelStorage: HotelStorage,
    navController: NavController,
) {
    Screen(

        verticalArrangement = Arrangement.Top,
        padding = 0
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            item {
                HotelDetailSImageSwiper(
                    images = hotelStorage.imageData.values.flatten().toList(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {

                    Text(
                        text = hotelStorage.hotelName,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = hotelStorage.hotelAddress,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )

                    HotelGalleryPhotos(
                        images = hotelStorage.imageData.values.flatten().toMutableList()
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )
                    HotelDetailsDetails()

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )

                    HotelDescription(
                        description = hotelStorage.hotelDescription
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )

                    HotelDetailsList(
                        list = hotelStorage.restrictions,
                        categoryName = "Restrictions"
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )

                    HotelDetailsList(
                        list = hotelStorage.houseAmenities,
                        categoryName = "House Amenities"
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )
                    HotelDetailsList(
                        list = hotelStorage.housePoliciesDos,
                        categoryName = "House Policies Dos"
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )
                    HotelDetailsList(
                        list = hotelStorage.housePoliciesDonts,
                        categoryName = "House Policies Donts"
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    )

                    if (hotelStorage.reviews.isNotEmpty()) {
                        Text(
                            text = "Reviews",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 19.sp,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                    }

                    hotelStorage.reviews.forEach {
                        HotelDetailsReview(
                            userName = it.name,
                            date = it.date.toString(),
                            rating = it.ratingLevel.toString(),
                            message = it.ratingNote
                        )
                    }
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "₹${hotelStorage.minPrice} /Night",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 50.dp, vertical = 15.dp)
                    .clickable(onClick = {
                        navController.navigate("${BookingScreenRoute.route}/id=${hotelStorage._id}")
                    })
            ) {
                Text(
                    text = "Book",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}