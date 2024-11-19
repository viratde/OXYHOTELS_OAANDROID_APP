package com.oxyhotel.feature_auth.presentation.welcome.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.oxyhotel.R
import com.oxyhotel.feature_auth.presentation.welcome.composables.SelectedIndex
import com.oxyhotel.feature_auth.presentation.welcome.composables.WelcomeScreen
import com.oxyhotel.feature_auth.presentation.welcome.composables.WelcomeScreenExtra

import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun WelcomeScreenStack(
    onGetOut:() -> Unit = {}
) {
    var activeIndex by remember {
        mutableStateOf(0)
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val lifecycleOwner = rememberCoroutineScope()
    val offsetXState = remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    var offsetX by remember { mutableStateOf(0f) }

    fun moveToIndex(index: Int) {
        val maxOffsetPx = with(density) { (3 * screenWidth).dp.toPx() }
        activeIndex = index
        lifecycleOwner.launch {
            animate(
                initialValue = offsetX,
                targetValue = -(maxOffsetPx * index.toFloat() / 3f),
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow
                )
            ) { value, _ ->
                offsetX = value
                offsetXState.value = offsetX / maxOffsetPx
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        WelcomeScreenExtra(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.roundToInt(), 0) },
            onNext = { moveToIndex(1) })

        WelcomeScreen(
            lightResourceId = R.drawable.welcome_screen_first,
            darkResourceId = R.drawable.welcome_screen_first,
            mainText = stringResource(id = R.string.welcome_screen_first_text),
            normalText = stringResource(id = R.string.welcome_screen_first_support_text),
            selectedIndex = SelectedIndex.FIRST,
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.roundToInt() + ((screenWidth * density.density).roundToInt()) * 1,
                        0
                    )
                },
            onNext = { moveToIndex(2) }
        )
        WelcomeScreen(
            lightResourceId = R.drawable.welcome_screen_second,
            darkResourceId = R.drawable.welcome_screen_second,
            mainText = stringResource(id = R.string.welcome_screen_second_text),
            normalText = stringResource(id = R.string.welcome_screen_second_support_text),
            selectedIndex = SelectedIndex.SECOND,
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.roundToInt() + ((screenWidth * density.density).roundToInt()) * 2,
                        0,
                    )
                },
            onNext = { moveToIndex(3) }
        )
        WelcomeScreen(
            lightResourceId = R.drawable.welcome_screen_third,
            darkResourceId = R.drawable.welcome_screen_third,
            mainText = stringResource(id = R.string.welcome_screen_third_text),
            normalText = stringResource(id = R.string.welcome_screen_third_support_text),
            selectedIndex = SelectedIndex.THIRD,
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.roundToInt() + ((screenWidth * density.density).roundToInt()) * 3,
                        0
                    )
                },
            onNext = { onGetOut() }
        )
    }

}