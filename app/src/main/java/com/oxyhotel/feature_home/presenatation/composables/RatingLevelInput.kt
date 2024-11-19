package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.oxyhotel.R
import com.oxyhotel.feature_home.presenatation.utils.RatingLevel
import com.oxyhotel.ui.theme.paintedStarColor

@Composable
fun RatingLevelInput(
    level: RatingLevel,
    onLevelChange: (RatingLevel) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RatingLevel.values().forEach { rate ->
            IconButton(
                onClick = { onLevelChange(rate) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Star Icon For Rating",
                    tint = if (rate.ordinal <= level.ordinal) paintedStarColor else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }

}