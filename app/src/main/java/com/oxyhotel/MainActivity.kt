package com.oxyhotel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.oxyhotel.feature_auth.presentation.AuthStack
import com.oxyhotel.feature_auth.presentation.auth.viewmodels.AuthViewModel
import com.oxyhotel.feature_home.presenatation.composables.UpdateButton
import com.oxyhotel.feature_home.presenatation.viewmodels.AllBookingsViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.HomeStackViewModel
import com.oxyhotel.feature_home.presenatation.viewmodels.ProfileViewModel
import com.oxyhotel.ui.theme.OxyhotelsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val firebase by lazy {
        FirebaseMessaging.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var isLoaded by rememberSaveable() {
                mutableStateOf(false)
            }
            var token by rememberSaveable() {
                mutableStateOf("")
            }

            LaunchedEffect(key1 = true) {
                firebase.token.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val aToken = it.result
                        if (aToken != null) {
                            token = aToken
                        }
                    }
                    isLoaded = true
                }
            }


            if (isLoaded) {

                OxyhotelsTheme {

                    val authViewModel: AuthViewModel = hiltViewModel()
                    val state = authViewModel.state.collectAsState()

                    LaunchedEffect(key1 = token) {
                        if (token.isNotEmpty() && isLoaded) {
                            withContext(Dispatchers.IO) {
                                authViewModel.firstTimeGetAuth(token)
                            }
                        } else {
                            authViewModel.signOut()
                        }
                    }

                    if (state.value.isUpdateRequired) {
                        UpdateButton()
                    } else if (!state.value.isAuthenticated) {

                        val context = LocalContext.current

                        LaunchedEffect(key1 = state.value.isError) {
                            if (state.value.isError) {
                                Toast.makeText(
                                    context,
                                    state.value.errorMessage,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                authViewModel.clearMessage()
                            }
                        }

                        AuthStack(
                            authViewModel = authViewModel,
                            state = state.value,
                        )
                    } else {
                        val homeStackViewModel: HomeStackViewModel = hiltViewModel()
                        val allBookingsViewModel: AllBookingsViewModel = hiltViewModel()
                        val profileViewModel: ProfileViewModel = hiltViewModel()
                        MainStack(
                            homeStackViewModel = homeStackViewModel,
                            allBookingsViewModel = allBookingsViewModel,
                            profileViewModel = profileViewModel
                        )
                    }

                }
            }
        }
    }
}
