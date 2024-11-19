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
import com.oxyhotel.feature_home.domain.model.Location
import com.oxyhotel.feature_home.presenatation.utils.SelectedCity

@Composable
fun HotelLocationChipsSelector(
    locations: List<Location>,
    selectedCity: SelectedCity,
    hasUserLocation: Boolean,
    onSelect: (SelectedCity) -> Unit
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {

        item {

            Text(
                text = "All",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp
                ),
                color = if (selectedCity is SelectedCity.All) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = {
                        onSelect(
                            SelectedCity.All
                        )
                    })
                    .background(if (selectedCity is SelectedCity.All) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            )
        }

        if (hasUserLocation) {

            item {

                Text(
                    text = "NearBy",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp
                    ),
                    color = if (selectedCity is SelectedCity.NearBy) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = {
                            onSelect(
                                SelectedCity.NearBy
                            )
                        })
                        .background(if (selectedCity is SelectedCity.NearBy) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground)
                        .padding(horizontal = 15.dp, vertical = 12.dp)
                )
            }

        }

        itemsIndexed(locations) { _, it ->
            Text(
                text = it.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp
                ),
                color = if (selectedCity is SelectedCity.SelectedLocation && selectedCity.location._id == it._id) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = {
                        onSelect(
                            SelectedCity.SelectedLocation(it)
                        )
                    })
                    .background(if (selectedCity is SelectedCity.SelectedLocation && selectedCity.location._id == it._id) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            )
        }
    }
}