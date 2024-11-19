package com.oxyhotel.feature_auth.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Button(
    modifier: Modifier = Modifier,
    colors:ButtonColors,
    onClick:() -> Unit,
    content: @Composable RowScope.() -> Unit,
){
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors
    ) {
        content()
    }
}