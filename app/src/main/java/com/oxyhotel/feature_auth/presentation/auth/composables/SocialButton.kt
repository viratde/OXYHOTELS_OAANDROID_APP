package com.oxyhotel.feature_auth.presentation.auth.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.feature_auth.presentation.common.Button

@Composable
fun SocialButton(
    painterResource: Int,
    socialName: String,
    isAuthenticating: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .widthIn(max = 350.dp, min = 320.dp),
        onClick = {
            if (!isAuthenticating) {
                onClick()
            }
        }) {
        Image(
            painter = painterResource(id = painterResource),
            contentDescription = "$socialName icon",
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(27.dp)
                .height(27.dp)
                .clip(CircleShape)

        )
        Text(
            text = "Continue With $socialName",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp, top = 3.dp)
        )
    }
}


@Composable
fun SocialIconButton(
    painterResource: Int,
    socialName: String,
    isAuthenticating: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .widthIn(max = 350.dp, min = 320.dp),
        onClick = {
            if (!isAuthenticating) {
                onClick()
            }
        }) {
        Icon(
            painter = painterResource(id = painterResource),
            contentDescription = "$socialName icon",
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(27.dp)
                .height(27.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.background
        )
        Text(
            text = "Continue With $socialName",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp, top = 3.dp)
        )
    }
}

@Composable
fun SocialVectorIconButton(
    painterResource: ImageVector,
    socialName: String,
    isAuthenticating: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .widthIn(max = 350.dp, min = 320.dp),
        onClick = {
            if (!isAuthenticating) {
                onClick()
            }
        }) {
        Icon(
            imageVector = painterResource,
            contentDescription = "$socialName icon",
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(27.dp)
                .height(27.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.background
        )
        Text(
            text = "Continue With $socialName",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp, top = 3.dp)
        )
    }
}