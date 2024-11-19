package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HotelChipsSelector(
    options: List<String>,
    selectedValue: String,
    onSelect: (String) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        itemsIndexed(options) { _, it ->

            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp
                ),
                color = if (it == selectedValue) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = { onSelect(it) })
                    .background(if (it != selectedValue) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            )
        }
    }
}