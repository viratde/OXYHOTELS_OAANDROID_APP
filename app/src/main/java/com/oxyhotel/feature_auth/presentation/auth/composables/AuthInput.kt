package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthInput(
    value:String,
    onValueChange:(String) -> Unit,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    leadingIcon:Int? = null,
    trailingIcon:Int?= null,
    placeholderText:String,
    enabled:Boolean = true
){

    var isFocused by remember {
        mutableStateOf(false)
    }

    val borderWidth = animateDpAsState(
        targetValue = if(isFocused) 1.dp else (-0.1).dp,
        animationSpec = tween(200), label = ""
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled =enabled,
        textStyle = MaterialTheme.typography.bodySmall.copy(
            color= MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier
            .onFocusChanged { isFocused = it.isFocused }.padding(top = 3.dp),
        visualTransformation = VisualTransformation.None,
        singleLine = true,
        cursorBrush = Brush.linearGradient(listOf( MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.secondary)),
        decorationBox = {
            Row(modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 30.dp)
                .widthIn(max = 350.dp, min = 320.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .border(
                    borderWidth.value,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 15.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(leadingIcon != null){
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .width(20.dp)
                            .height(20.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                ){

                    Text(
                        text =if(value.isEmpty()) placeholderText else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                    )
                    it()
                }
                if(trailingIcon != null){
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp, start = 10.dp)
                            .width(20.dp)
                            .height(20.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    )

}