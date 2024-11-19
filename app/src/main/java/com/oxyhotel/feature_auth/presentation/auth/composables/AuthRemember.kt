package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthRemember(
    isChecked:Boolean,
    onCheckedChange:(Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .padding(vertical = 9.dp)
            .width(300.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(7.dp))
                .width(20.dp)
                .height(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Transparent,
                uncheckedColor = Color.Transparent,
                checkmarkColor = MaterialTheme.colorScheme.secondary
            )
        )
        Text(
            text = "Remember me",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 7.dp,top =3.dp)
        )
    }
}