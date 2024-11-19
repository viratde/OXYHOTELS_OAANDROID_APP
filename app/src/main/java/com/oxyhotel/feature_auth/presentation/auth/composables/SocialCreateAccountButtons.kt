package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.oxyhotel.R

@Composable
fun SocialCreateAccountButtons(
    onTwitter: () -> Unit,
    onGoogle: () -> Unit,
    onApple: () -> Unit,
    isAuthenticating: Boolean
){
    Row(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .width(300.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SocialImageCreateAccountButton(
            onClick = {if(!isAuthenticating){onTwitter()} },
            painterResourceId = R.drawable.twitter
        )
        SocialImageCreateAccountButton(
            onClick = {if(!isAuthenticating){onGoogle()}},
            painterResourceId = R.drawable.google
        )
        SocialIconCreateAccountButton(
            onClick = {if(!isAuthenticating){onApple()}},
            painterResourceId = R.drawable.apple
        )
    }
}
@Composable
fun SocialIconCreateAccountButton(
    onClick:() -> Unit,
    painterResourceId:Int
){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .border(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
            .clickable(onClick = { onClick() })
    ) {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 14.dp)
                .width(30.dp)
                .height(30.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun SocialImageCreateAccountButton(
    onClick:() -> Unit,
    painterResourceId:Int
){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .border(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
            .clickable(onClick = { onClick() })
    ) {
        Image(
            painter = painterResource(id = painterResourceId),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 14.dp)
                .width(30.dp)
                .height(30.dp)
        )
    }
}