package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthHeaderText(
    text:String,
    fontSize:Int = 35
){
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(fontSize = fontSize.sp),
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 10.dp)
    )
}