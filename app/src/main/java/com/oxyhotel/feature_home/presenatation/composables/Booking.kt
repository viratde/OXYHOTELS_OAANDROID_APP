package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.model.HotelStorage

@Composable
fun Booking(
    booking: BookingStorage,
    hotelStorage: HotelStorage,
    isCancelling: Boolean,
    onClick: () -> Unit,
    onCancel: (String) -> Unit,
    onRate: (String) -> Unit
) {

    val painter =
        rememberAsyncImagePainter(model = "${Constant.domain}${hotelStorage.imageData.values.flatten()[0]}")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = { onClick() })
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .width(110.dp)
                    .height(110.dp)

            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 110.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hotelStorage.hotelName,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = "OXY${booking.bookingId}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = hotelStorage.hotelAddress,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                    )
                )
                val textValue = if (booking.isCancelled) {
                    "Cancelled"
                } else if (booking.hasNotShown) {
                    "No Show"
                } else if (booking.hasCheckedOut) {
                    "Completed"
                } else if (booking.hasCheckedIn) {
                    "Ongoing"
                } else {
                    "Upcoming"
                }

                Text(
                    text = textValue,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .padding(end = 20.dp, top = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 15.dp, vertical = 12.dp)
                )
                if (textValue == "Upcoming") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Row(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                                .clickable(onClick = { onCancel(booking.bookingId) })
                                .animateContentSize()
                        ) {
                            if (isCancelling) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = MaterialTheme.colorScheme.background
                                )
                            } else {
                                Text(
                                    text = "Cancel",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                        }
                    }
                }

                if (textValue == "Completed") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Row(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                                .clickable(onClick = { onRate(booking.bookingId) })
                        ) {
                            Text(
                                text = "Rate Now",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 13.sp,
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