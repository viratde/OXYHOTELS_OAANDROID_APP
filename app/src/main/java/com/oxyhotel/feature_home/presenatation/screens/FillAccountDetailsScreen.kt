package com.oxyhotel.feature_home.presenatation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.oxyhotel.feature_auth.presentation.common.HeaderWithBackButton
import com.oxyhotel.feature_home.presenatation.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch


@Composable
fun FillAccountDetailsScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
) {

    val state = profileViewModel.state.collectAsState()

    var name by rememberSaveable {
        mutableStateOf(state.value.userData?.name ?: "")
    }

    var dob by rememberSaveable {
        mutableStateOf(state.value.userData?.dob ?: "")
    }

    var email by rememberSaveable {
        mutableStateOf(state.value.userData?.email ?: "")
    }

    var phone by rememberSaveable {
        mutableStateOf(state.value.userData?.phoneNumber ?: "")
    }

    val focusManager = LocalFocusManager.current


    val context = LocalContext.current
    val life = LocalLifecycleOwner.current

    val onDone = {
        life.lifecycleScope.launch {
            profileViewModel.updateData(
                name = name,
                email = email,
                phone = phone,
                dob = dob
            )
        }
    }

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(key1 = state.value.isError) {

        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            profileViewModel.clearMessage()
        }

    }

    Screen(
        verticalArrangement = Arrangement.Top,
    ) {

        HeaderWithBackButton(
            text = "My Profile",
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
            enabled = state.value.userData?.email == null || state.value.userData!!.email?.isEmpty() == true
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
            enabled = state.value.userData?.phoneNumber == null || state.value.userData!!.phoneNumber?.isEmpty() == true
        )

        Spacer(modifier = Modifier.weight(4f))

        AuthButton(
            text = "Continue",
            isLoading = state.value.isLoading,
            onClick = {
                onDone()
            },
        )

    }
}
