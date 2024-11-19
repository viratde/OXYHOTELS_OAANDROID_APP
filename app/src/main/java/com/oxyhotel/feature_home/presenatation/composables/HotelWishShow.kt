package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.oxyhotel.R
import com.oxyhotel.constants.Constant

@Composable
fun RowScope.HotelWishShow(
    hotelName: String,
    hotelAddress: String,
    hotelPrice: Number,
    hotelNoOfRatings: Int,
    modifier: Modifier = Modifier
        .padding(start = 10.dp, end = 5.dp, bottom = 15.dp, top = 10.dp)
        .clip(RoundedCornerShape(10.dp))
        .weight(1f),
    hotelRating: Number,
    hotelImageUri: String,
    isWishlist: Boolean,
    onWishlist: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
) {

    val painter = rememberAsyncImagePainter(
        model = "${Constant.domain}$hotelImageUri".toUri()
    )

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .clickable(onClick = { onClick() })
            .padding(bottom = 10.dp)
    ) {

        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentDescription = ""
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 5.dp, horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = ""
                    )
                    Text(
                        text = hotelRating.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 7.sp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = hotelName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = hotelAddress,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                        ),
                    )
                    Text(
                        text = "₹$hotelPrice /per night",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Icon(
                    painter = painterResource(id = if (isWishlist) R.drawable.bookmark else R.drawable.bookmark_add_icon),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "",
                    modifier = Modifier.clickable(onClick = { onWishlist(!isWishlist) })
                )
            }

        }

    }
}