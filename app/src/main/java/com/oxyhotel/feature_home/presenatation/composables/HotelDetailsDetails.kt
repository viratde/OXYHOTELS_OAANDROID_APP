package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.R

@Composable
fun HotelDetailsDetails(

) {

    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = "Details",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        )

        Row(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.hotel_icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Text(
                    text = "Hotels",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bathroom_icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Text(
                    text = "Bathrooms",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bedroom_icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Text(
                    text = "Bedrooms",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.spacious_icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Text(
                    text = "Spacious",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

            }

        }
    }

}