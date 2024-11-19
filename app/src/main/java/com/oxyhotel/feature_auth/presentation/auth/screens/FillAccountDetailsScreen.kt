package com.oxyhotel.feature_auth.presentation.auth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.oxyhotel.R
import com.oxyhotel.common.composables.Screen
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthButton
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthDateInput
import com.oxyhotel.feature_auth.presentation.auth.composables.AuthInput
import com.oxyhotel.feature_auth.presentation.auth.composables.UserImageEdit
import com.oxyhotel.feature_auth.presentation.auth.states.AuthState
import com.oxyhotel.feature_auth.presentation.auth.states.VerificationResponse
import com.oxyhotel.feature_auth.presentation.auth.viewmodels.AuthViewModel
import com.oxyhotel.feature_auth.presentation.common.HeaderWithBackButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FillAccountDetailsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    verificationResponse: VerificationResponse,
    state: AuthState
) {

    var name by rememberSaveable {
        mutableStateOf(verificationResponse.name ?: "")
    }

    var dob by rememberSaveable {
        mutableStateOf(verificationResponse.dob ?: "")
    }

    var email by rememberSaveable {
        mutableStateOf(verificationResponse.email ?: "")
    }

    var phone by rememberSaveable {
        mutableStateOf(verificationResponse.phoneNumber ?: "")
    }
    val context = LocalContext.current
    val life = LocalLifecycleOwner.current

    val onDone = {
        life.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                authViewModel.signUp(
                    name = name,
                    email = email,
                    phone = phone,
                    dob = dob,
                    verifiedToken = verificationResponse.verifiedToken!!
                )
            }
        }
    }
    val focusManager = LocalFocusManager.current



    Screen(
        verticalArrangement = Arrangement.Top
    ) {

        HeaderWithBackButton(
            text = "Fill Your Details",
            onClick = {
                navController.popBackStack()
            },
        )

        Spacer(modifier = Modifier.weight(1f))
        UserImageEdit(painterResourceId = R.drawable.user_icon)
        Spacer(modifier = Modifier.weight(1f))

        AuthInput(
            value = name,
            onValueChange = { name = it },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            trailingIcon = null,
            placeholderText = "Full Name",
            enabled = true
        )

        AuthDateInput(
            selectedDate = dob,
            onDateChange = { dob = it },
            placeholderText = "Choose Birth Datex"
        )

        AuthInput(
            value = email,
            onValueChange = { email = it },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            trailingIcon = null,
            placeholderText = "Email",
            enabled = verificationResponse.email.isNullOrEmpty()
        )

        AuthInput(
            value = phone,
            onValueChange = { phone = it },
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            trailingIcon = null,
            placeholderText = "Phone",
            enabled = verificationResponse.phoneNumber.isNullOrEmpty()
        )

        Spacer(modifier = Modifier.weight(4f))

        AuthButton(
            text = "Continue",
            isLoading = state.isAuthenticating,
            onClick = {
                onDone()
            },
        )

    }


}