package com.oxyhotel.feature_auth.presentation.welcome.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class SelectedIndex{
    FIRST,
    SECOND,
    THIRD
}

@Composable
fun WelcomeSelectionIndicator(
    selectedIndex: SelectedIndex,
    selectedColor:Color,
    unselectedColor:Color,
    modifier : Modifier = Modifier.fillMaxWidth()
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        SelectedIndex.values().forEach {
            if(it == selectedIndex){
                Row(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .width(24.dp)
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                ) {

                }
            }else{
                Row(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .width(8.dp)
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(unselectedColor)
                ) {

                }
            }
        }
    }
}