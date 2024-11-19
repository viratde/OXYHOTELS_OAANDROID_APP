package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.R

@Composable
fun CustomBookRoom(
    mainText: String,
    onClick: (Int) -> Unit,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mainText,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary
            )
        )


        Row(
            modifier = Modifier
                .width(150.dp)
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.minus),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(10.dp)
                    )
                    .clickable(onClick = {
                        onClick(-1)
                    })
                    .padding(horizontal = 15.dp, vertical = 9.dp)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(10.dp)
                    )
                    .clickable(onClick = {
                        onClick(1)
                    })
                    .padding(horizontal = 13.5.dp, vertical = 9.dp)
            )

        }
    }
}