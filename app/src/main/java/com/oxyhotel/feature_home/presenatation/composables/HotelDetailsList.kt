package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HotelDetailsList(
    list: List<String>,
    categoryName: String,
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {

        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        list.map {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier
                        .padding(end = 15.dp, top = 6.dp)
                        .width(11.dp)
                        .height(11.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f))
                ) {

                }

                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

        }

    }
}