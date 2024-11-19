package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.R
import com.oxyhotel.constants.Constant

@Composable
fun HotelShowSmallH(
    hotelName: String,
    hotelAddress: String,
    hotelPrice: Number,
    hotelRating: Number,
    hotelNoOfRatings: Number,
    hotelImageUri: String,
    isWishlist: Boolean,
    onWishlist: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
) {

    val painter = rememberAsyncImagePainter(model = "${Constant.domain}$hotelImageUri".toUri())

    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .clickable(onClick = { onClick() }),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Icon(
                painter = painterResource(id = if (isWishlist) R.drawable.bookmark else R.drawable.bookmark_add_icon),
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = "",
                modifier = Modifier
                    .clickable(onClick = { onWishlist(!isWishlist) })
                    .padding(end = 15.dp, bottom = 20.dp)
            )
        }


        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .width(110.dp)
                    .height(110.dp)
            )


            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    text = hotelName,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )

                Text(
                    text = hotelAddress,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.padding(top = 5.dp)
                )

                Text(
                    text = "₹$hotelPrice /per night",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.padding(top = 5.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = ""
                    )

                    Text(
                        text = hotelRating.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 8.sp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(start = 3.dp)
                    )

                    Text(
                        text = "($hotelNoOfRatings Ratings)",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 8.sp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }

            }
        }


    }

}