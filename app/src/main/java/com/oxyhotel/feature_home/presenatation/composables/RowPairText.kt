package com.oxyhotel.feature_home.presenatation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnScope.RowPairText(
    placeholder: String,
    value: String
) {

    Column(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = placeholder,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
            )
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        )

    }

}