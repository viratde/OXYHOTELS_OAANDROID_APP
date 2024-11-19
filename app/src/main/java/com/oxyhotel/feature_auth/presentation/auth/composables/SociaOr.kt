package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SocialDivider(
    text:String
){
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .width(300.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .height(2.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.secondary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Box(modifier = Modifier
            .weight(1f)
            .height(2.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.secondary)
        )
    }
}