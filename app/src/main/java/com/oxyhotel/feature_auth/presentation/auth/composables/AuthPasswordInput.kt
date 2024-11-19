package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.oxyhotel.R


@Composable
fun AuthPasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    leadingIcon: Int,
    placeholderText: String,
    isEnabled: Boolean
){
    var isFocused by remember {
        mutableStateOf(false)
    }

    var isVisible by remember {
        mutableStateOf(false)
    }

    val borderWidth = animateDpAsState(
        targetValue = if(isFocused) 1.dp else (-0.1).dp,
        animationSpec = tween(200)
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle =  MaterialTheme.typography.bodySmall.copy(
            color= MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.onFocusChanged{isFocused = it.isFocused}.padding(top = 3.dp),
        visualTransformation = if(isVisible) VisualTransformation.None else PasswordVisualTransformation() ,
        singleLine = true,
        cursorBrush = Brush.linearGradient(listOf( MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.secondary)),
        decorationBox = {
            Row(modifier = Modifier
                .padding(vertical = 10.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .border(borderWidth.value,MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
                .padding(horizontal = 15.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .width(20.dp)
                        .height(20.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Box(modifier = Modifier.weight(1f)){
                    if(value.isEmpty()){
                        Text(
                            text = placeholderText,
                            style =  MaterialTheme.typography.bodySmall.copy(
                                color= MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                    it()
                }
                Icon(
                    painter = painterResource(id = if(isVisible) R.drawable.visibility else R.drawable.visibility_off),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .width(20.dp)
                        .height(20.dp)
                        .clickable(onClick = {isVisible = !isVisible}),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        enabled = isEnabled
    )
}