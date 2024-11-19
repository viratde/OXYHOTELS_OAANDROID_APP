package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HotelDescription(
    description: String
) {
    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = "Description",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        )

        Text(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            text = description,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        )

    }
}