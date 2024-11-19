package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.R

@Composable
fun HotelDetailsReview(
    userName: String,
    date: String,
    rating: String,
    message: String
) {

    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {

        Row {

            Icon(
                painter = painterResource(id = R.drawable.user_icon),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp)
            ) {

                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )

                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )

            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 7.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = rating,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 10.sp
                    ),
                    modifier = Modifier.padding(start = 9.dp)
                )
            }

        }
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f)
            ),
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        )
    }
}