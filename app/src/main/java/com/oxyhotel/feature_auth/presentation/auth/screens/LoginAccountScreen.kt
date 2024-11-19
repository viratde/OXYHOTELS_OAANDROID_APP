package com.oxyhotel.feature_auth.presentation.auth.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oxyhotel.R
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthButton
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthHeaderText
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthInput
import com.oxyhotel.feature_auth.presentation.auth.composables.SocialButton
import com.oxyhotel.feature_auth.presentation.auth.composables.SocialDivider
import com.oxyhotel.feature_auth.presentation.auth.states.AuthState
import com.oxyhotel.feature_auth.presentation.auth.viewmodels.AuthViewModel
import com.oxyhotel.ui.theme.OxyhotelsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginAccountScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    state: AuthState
) {
    var email by remember {
        mutableStateOf("")
    }
    var otp by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current


    val context = LocalContext.current

    val life = LocalLifecycleOwner.current

    var isNeedToSent by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                life.lifecycleScope.launch {
                    authViewModel.googleIntentParser(intent = it.data)
                }
            } else {
                authViewModel.googleIntentParser(it.data)
            }
        })

    fun onDone() {
        if (state.isOtpSent && !isNeedToSent) {
            life.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (otp.toIntOrNull() != null) {
                        authViewModel.verifyOtp(otp)
                    }
                }
            }
        } else {
            if ((email.isEmpty() || !email.contains("@")) && (email.toIntOrNull() != null || email.length != 10)) {
                Toast.makeText(context, "Please Enter Correct Email or Phone", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            life.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    authViewModel.sendOtpOnEmailOrPhone(email)
                }
            }
        }
    }

    Screen {


        Spacer(modifier = Modifier.weight(2f))

        AuthHeaderText(text = "Welcome to\nOxyhotels", fontSize = 34)

        Spacer(modifier = Modifier.weight(2f))

        AuthInput(
            value = email,
            onValueChange = {
                email = it; isNeedToSent = state.otpSentEmail != it && state.otpSentEmail != null
            },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            leadingIcon = R.drawable.mail,
            placeholderText = "Email/Phone",
        )

        AnimatedVisibility(visible = state.isOtpSent && !isNeedToSent) {
            AuthInput(
                value = otp,
                onValueChange = {
                    otp = it
                },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                leadingIcon = R.drawable.mail,
                placeholderText = "Otp",
                enabled = state.isOtpSent
            )
        }


        Spacer(modifier = Modifier.weight(0.5f))

        AuthButton(
            text = if (state.isOtpSent && !isNeedToSent) "Verify" else "Send Otp",
            onClick = { onDone() },
            isLoading = state.isAuthenticating
        )

        Spacer(modifier = Modifier.weight(1f))

        SocialDivider(text = "Or continue with")

        Spacer(modifier = Modifier.weight(1f))

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

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(widthDp = 1000, heightDp = 1000)
@Composable
fun LoginScreenPreview() {

    OxyhotelsTheme {

        Screen {


            Spacer(modifier = Modifier.weight(2f))

            AuthHeaderText(text = "Welcome to\nOxyhotels", fontSize = 34)

            Spacer(modifier = Modifier.weight(2f))

            AuthInput(
                value = "",
                onValueChange = {

                },
                keyboardActions = KeyboardActions(
                    onNext = { }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                leadingIcon = R.drawable.mail,
                placeholderText = "Email/Phone",
            )

            AnimatedVisibility(visible = true) {
                AuthInput(
                    value = "",
                    onValueChange = {

                    },
                    keyboardActions = KeyboardActions(
                        onNext = { }
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    leadingIcon = R.drawable.mail,
                    placeholderText = "Otp",
                    enabled = true
                )
            }


            Spacer(modifier = Modifier.weight(0.5f))

            AuthButton(
                text = "Verify",
                onClick = { },
                isLoading = false
            )

            Spacer(modifier = Modifier.weight(1f))

            SocialDivider(text = "Or continue with")

            Spacer(modifier = Modifier.weight(1f))

            SocialButton(
                painterResource = R.drawable.google,
                socialName = "Google",
                isAuthenticating = false,
                onClick = {

                }
            )

            Spacer(modifier = Modifier.weight(1f))

        }

    }
}