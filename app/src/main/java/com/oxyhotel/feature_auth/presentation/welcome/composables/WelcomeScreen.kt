package com.oxyhotel.feature_auth.presentation.welcome.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_auth.presentation.common.Button


@Composable
fun WelcomeScreen(
    lightResourceId: Int,
    darkResourceId: Int,
    mainText: String,
    normalText: String,
    selectedIndex: SelectedIndex,
    onNext: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val isDarkMode = isSystemInDarkTheme()

    Screen(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.statusBarsPadding(),
        padding = 0
    ) {
        Image(
            painter = painterResource(id = if (isDarkMode) darkResourceId else lightResourceId),
            contentDescription = "Welcome Screen",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            contentScale = ContentScale.Crop
        )

        Text(
            text = mainText,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 30.sp),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 15.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = normalText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 15.dp),
            textAlign = TextAlign.Center,
        )

        WelcomeSelectionIndicator(
            selectedIndex = selectedIndex,
            selectedColor = MaterialTheme.colorScheme.primary,
            unselectedColor = MaterialTheme.colorScheme.secondary,
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier
                .padding(bottom = 15.dp)
                .width(300.dp),
            onClick = { onNext() }) {
            Text(
                text = "Next",
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
    }
}