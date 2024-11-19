package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.feature_auth.presentation.common.Button

@Composable
fun AuthButton(
    text: String,
    modifier: Modifier = Modifier
        .padding(horizontal = 30.dp)
        .widthIn(max = 350.dp, min = 320.dp)
        .padding(vertical = 10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.background,
        containerColor = MaterialTheme.colorScheme.primary,
    ),
    onClick: () -> Unit,
    isLoading: Boolean = false
){
    Button(
        colors = colors,
        modifier = modifier,
        onClick = { if(!isLoading){onClick()} }
    ) {
        if(isLoading){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .width(25.dp)
                    .height(25.dp)
            )
        }else{
            Row(modifier = Modifier
                .padding(vertical = 5.dp)
                .height(27.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

