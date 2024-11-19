package com.oxyhotel.feature_auth.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oxyhotel.feature_auth.presentation.auth.screens.FillAccountDetailsScreen
import com.oxyhotel.feature_auth.presentation.auth.screens.LoginAccountScreen
import com.oxyhotel.feature_auth.presentation.auth.screens.SocialLoginScreen
import com.oxyhotel.feature_auth.presentation.auth.states.AuthState
import com.oxyhotel.feature_auth.presentation.auth.viewmodels.AuthViewModel
import com.oxyhotel.feature_auth.presentation.welcome.screens.WelcomeScreenStack


interface AuthStackRoute {
    val route: String
}

object SplashScreenRoute : AuthStackRoute {
    override val route: String = "Splash Screen Route"
}

object SocialLoginRoute : AuthStackRoute {
    override val route: String = "Social Login Screen Route"
}

object LoginAccountRoute : AuthStackRoute {
    override val route: String = "Login Account Route"
}

@Composable
fun AuthStack(
    authViewModel: AuthViewModel,
    state: AuthState,
) {


    val navController = rememberNavController()


    AnimatedVisibility(visible = state.verificationResponse.verifiedToken != null) {
        FillAccountDetailsScreen(
            navController = navController,
            authViewModel = authViewModel,
            verificationResponse = state.verificationResponse,
            state = state
        )
    }

    AnimatedVisibility(visible = state.verificationResponse.verifiedToken == null) {
        NavHost(navController = navController, startDestination = SplashScreenRoute.route) {
            composable(SplashScreenRoute.route) {
                WelcomeScreenStack(
                    onGetOut = {
                        navController.navigate(SocialLoginRoute.route) {
                            popUpTo(SplashScreenRoute.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }


            composable(
                SocialLoginRoute.route,
            ) {
                SocialLoginScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    state = state
                )
            }


            composable(LoginAccountRoute.route) {
                LoginAccountScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    state = state
                )
            }
        }
    }
}