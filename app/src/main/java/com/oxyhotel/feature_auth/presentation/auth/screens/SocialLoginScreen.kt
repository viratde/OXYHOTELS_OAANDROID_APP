package com.oxyhotel.feature_auth.presentation.auth.screens

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.oxyhotel.R
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_auth.presentation.LoginAccountRoute
import com.oxyhotel.feature_auth.presentation.SocialLoginRoute
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthButton
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthHeaderText
import com.oxyhotel.feature_auth.presentation.auth.composables.DifferentText
import com.oxyhotel.feature_auth.presentation.auth.composables.EmailButton
import com.oxyhotel.feature_auth.presentation.auth.composables.SocialButton
import com.oxyhotel.feature_auth.presentation.auth.composables.SocialDivider
import com.oxyhotel.feature_auth.presentation.auth.composables.SocialVectorIconButton
import com.oxyhotel.feature_auth.presentation.auth.states.AuthState
import com.oxyhotel.feature_auth.presentation.auth.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SocialLoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    state: AuthState
) {


    val context = LocalContext.current

    val isDarkTheme = isSystemInDarkTheme()
    val life = LocalLifecycleOwner.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == RESULT_OK) {
                life.lifecycleScope.launch {
                    authViewModel.googleIntentParser(intent = it.data)
                }
            } else {
                authViewModel.googleIntentParser(it.data)
            }
        })


    Screen {

        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.social_page_icon_night else R.drawable.social_page_icon),
            contentDescription = "logo",
            modifier = Modifier
                .height(200.dp)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        AuthHeaderText(text = "Let's You in")
        Spacer(modifier = Modifier.weight(1f))


        SocialVectorIconButton(
            painterResource = Icons.Default.Phone,
            socialName = "Phone",
            isAuthenticating = state.isAuthenticating,
            onClick = {
                navController.navigate(LoginAccountRoute.route) {
                    launchSingleTop = true
                    popUpTo(SocialLoginRoute.route)
                }
            }
        )


        SocialButton(
            painterResource = R.drawable.google,
            socialName = "Google",
            isAuthenticating = state.isAuthenticating,
            onClick = {
                authViewModel.startSignInWithGoogle(
                    context,
                    launcher
                )
            }
        )

        EmailButton(
            text = "Continue With Email",
            isLoading = false,
            onClick = {
                navController.navigate(LoginAccountRoute.route) {
                    launchSingleTop = true
                    popUpTo(SocialLoginRoute.route)
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        SocialDivider("Or")

        Spacer(modifier = Modifier.weight(1f))

        AuthButton(
            text = "Continue With Phone",
            isLoading = state.isAuthenticating,
            onClick = {
                navController.navigate(LoginAccountRoute.route) {
                    launchSingleTop = true
                    popUpTo(SocialLoginRoute.route)
                }
            }
        )


        DifferentText(
            text = "Don't have an account?",
            activeText = "Sign up",
            onClick = {
                navController.navigate(LoginAccountRoute.route) {
                    launchSingleTop = true
                    popUpTo(SocialLoginRoute.route)
                }
            }
        )
    }
}