package com.oxyhotel.feature_auth.presentation.welcome.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.oxyhotel.R
import com.oxyhotel.common.composables.Screen

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreenExtra(
    onNext: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val isDarkMode = isSystemInDarkTheme()
    val life = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        life.lifecycleScope.launch {
            delay(2000)
            onNext()
        }
    }

    Screen(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
        padding = 0
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = if (isDarkMode) R.drawable.welcome_screen_night else R.drawable.welcome_screen),
                contentDescription = "Welcome Screen",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color(0xFF0D0D0D),
                            0.5f to Color(0x002C2C2C),
                            startY = Float.POSITIVE_INFINITY,
                            endY = 500f
                        )
                    ),
                verticalArrangement = Arrangement.Bottom
            ) {

                Text(
                    text = stringResource(id = R.string.welcome_screen_main),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .offset { IntOffset(0, 20) }
                )

                Text(
                    text = stringResource(id = R.string.company_name),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontSize = 70.sp,
                    ),
                    modifier = Modifier.padding(start = 20.dp)
                )

                Text(
                    text = stringResource(id = R.string.welcome_screen_normal),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                )

            }
        }
    }
}