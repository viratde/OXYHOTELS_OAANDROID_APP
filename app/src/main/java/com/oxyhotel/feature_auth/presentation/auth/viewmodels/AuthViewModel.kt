package com.oxyhotel.feature_auth.presentation.auth.viewmodels


import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.oxyhotel.R
import com.oxyhotel.constants.Constant
import com.oxyhotel.feature_auth.domain.use_cases.AuthUseCases
import com.oxyhotel.feature_auth.presentation.auth.states.AuthResponse
import com.oxyhotel.feature_auth.presentation.auth.states.AuthState
import com.oxyhotel.feature_auth.presentation.auth.states.SignupResponse
import com.oxyhotel.feature_auth.presentation.auth.states.VerificationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val client: HttpClient
) : ViewModel() {


    private val _state = MutableStateFlow(AuthState())
    val state = _state


    suspend fun firstTimeGetAuth(fToken: String) {

        val authData = authUseCases.getAuthData()

        if (authData?.authToken != null) {

            val jsonObject = JSONObject()
            jsonObject.put("authToken", authData.authToken)
            jsonObject.put("fToken", fToken)

            val response = client.post(Constant.verifyTokenRoute) {
                headers {
                    append("Content-Type", "application/json")
                }
                setBody(jsonObject.toString())
            }.body<String>()


            val rData = Gson().fromJson(response, AuthResponse::class.java)

            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = "Please try after some time.",
                        isAuthenticated = false,
                    )
                }
                return
            }
            _state.update {
                state.value.copy(
                    isAuthenticated = true,
                    isUpdateRequired = Constant.currentVersion != rData.data
                )
            }
        }
    }


    suspend fun checkTokenAndSetToken(token: String) {

    }

    suspend fun sendOtpOnEmailOrPhone(
        email: String,
    ) {

        _state.update { state.value.copy(isAuthenticating = true) }

        if ((email.isEmpty() || !email.contains("@")) && (email.toIntOrNull() != null || email.length != 10)) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please Enter Correct Email or Phone",
                    isAuthenticating = false
                )
            }
            return
        }

        try {

            val reqData = JSONObject()
            reqData.put("data", email)

            val response = client.post(Constant.getOtpOnEmailOrPhone) {
                headers {
                    append("Content-Type", "application/json")
                }
                setBody(reqData.toString())
            }.body<String>()

            val respData = Gson().fromJson(response, AuthResponse::class.java)

            if (!respData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = respData.message,
                        isAuthenticating = false
                    )
                }
                return
            }

            _state.update {
                state.value.copy(
                    isOtpSent = true,
                    isAuthenticating = false,
                    otpSentEmail = email,
                    otpToken = respData.data
                )
            }
        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time",
                    isAuthenticating = false
                )
            }
            return
        }

    }

    suspend fun verifyOtp(
        otp: String,
    ) {

        _state.update {
            state.value.copy(isAuthenticating = true)
        }

        if (otp.length != 6) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please Enter Correct Otp",
                    isAuthenticating = false
                )
            }
            return
        }

        try {

            val reqData = JSONObject()
            reqData.put("otp", otp)
            reqData.put("verifyToken", state.value.otpToken)

            val response = client.post(Constant.verifyOtpRoute) {
                headers {
                    append("Content-Type", "application/json")
                }
                setBody(reqData.toString())
            }.body<String>()

            val respData = Gson().fromJson(response, AuthResponse::class.java)

            if (!respData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = respData.message,
                        isAuthenticating = false
                    )
                }
                return
            }

            val parseData = Gson().fromJson(respData.data, VerificationResponse::class.java)
            if (parseData.verifiedToken != null) {
                _state.value = state.value.copy(
                    isAuthenticating = false,
                    isAuthenticated = false,
                    verificationResponse = parseData,
                    isError = true,
                    errorMessage = "Verified Successfully"
                )
                return
            } else if (parseData.authToken != null) {

                val authData = SignupResponse(
                    authToken = parseData.authToken,
                    name = parseData.name,
                    email = parseData.email,
                    dob = parseData.dob,
                    phoneNumber = parseData.phoneNumber,
                    gender = null
                )
                authUseCases.setAuthData(authData)

                _state.value = state.value.copy(
                    isAuthenticating = false,
                    isAuthenticated = true,
                    isError = true,
                    errorMessage = "Logged In Successfully"
                )
                return
            }

            _state.value =
                state.value.copy(isOtpSent = true, isAuthenticating = false, isAuthenticated = true)
        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time",
                    isAuthenticating = false
                )
            }
            return
        }
    }

    fun startSignInWithGoogle(
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        _state.update { state.value.copy(isAuthenticating = true) }

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.web_client_id))
                .requestEmail()
                .build()

        val user = GoogleSignIn.getLastSignedInAccount(context)
        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

        if (user != null) {
            mGoogleSignInClient.signOut().addOnSuccessListener {
                launcher.launch(mGoogleSignInClient.signInIntent)
            }.addOnFailureListener {
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = "Please try after some time",
                        isAuthenticating = false
                    )
                }
            }
        } else {
            launcher.launch(mGoogleSignInClient.signInIntent)
        }

    }

    fun googleIntentParser(intent: Intent?) {

        _state.update { state.value.copy(isAuthenticating = true) }

        if (intent != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            task.addOnSuccessListener {
                val code = it.idToken
                println(it.email)
                if (code == null) {
                    _state.update {
                        state.value.copy(
                            isError = true,
                            errorMessage = "Please try after some time(code not found)",
                            isAuthenticating = false
                        )
                    }
                    return@addOnSuccessListener
                }

                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        signInWithGoogle(code)
                    }
                }
            }.addOnFailureListener { err ->
                err.printStackTrace()
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = err.localizedMessage ?: "Please try after some time",
                        isAuthenticating = false
                    )
                }
            }
        } else {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time",
                    isAuthenticating = false
                )
            }
        }

    }

    private suspend fun signInWithGoogle(
        code: String
    ) {

        _state.update { AuthState(isAuthenticating = true) }

        try {

            val reqData = JSONObject()
            reqData.put("code", code)

            val response = client.post(Constant.verifyGoogleCodeRoute) {
                headers {
                    append("Content-Type", "application/json")
                }
                setBody(reqData.toString())
            }.body<String>()

            val respData = Gson().fromJson(response, AuthResponse::class.java)

            if (!respData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = respData.message,
                        isAuthenticating = false
                    )
                }
                return
            }

            val parseData = Gson().fromJson(respData.data, VerificationResponse::class.java)

            if (parseData.verifiedToken != null) {
                _state.value = state.value.copy(
                    isAuthenticating = false,
                    isAuthenticated = false,
                    verificationResponse = parseData,
                    isError = true,
                    errorMessage = "Verified Successfully"
                )
                return
            } else if (parseData.authToken != null) {

                val authData = SignupResponse(
                    authToken = parseData.authToken,
                    name = parseData.name,
                    email = parseData.email,
                    dob = parseData.dob,
                    phoneNumber = parseData.phoneNumber,
                    gender = null
                )
                authUseCases.setAuthData(authData)

                _state.value = state.value.copy(
                    isAuthenticating = false,
                    isAuthenticated = true,
                    isError = true,
                    errorMessage = "Logged In Successfully"
                )
                return
            }

            _state.value =
                state.value.copy(
                    isAuthenticating = false,
                    isAuthenticated = false
                )
        } catch (err: Exception) {
            println("error is $err")
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time",
                    isAuthenticating = false
                )
            }
            return
        }

    }


    fun signOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authUseCases.deleteAuthData()
                _state.update {
                    state.value.copy(isAuthenticated = false)
                }
            }
        }
    }

    suspend fun signUp(
        name: String,
        email: String,
        phone: String,
        dob: String,
        verifiedToken: String
    ) {
        _state.value = state.value.copy(
            isAuthenticating = true
        )

        if (name.isEmpty()) {
            _state.value = state.value.copy(
                isError = true,
                isAuthenticating = false,
                errorMessage = "Please Enter Correct Name"
            )
            return
        }

        if (email.isEmpty() || !email.contains("@")) {
            _state.value = state.value.copy(
                isError = true,
                isAuthenticating = false,
                errorMessage = "Please Enter Correct Email"
            )
            return
        }


        if (phone.isEmpty() || phone.toDoubleOrNull() == null) {
            _state.value = state.value.copy(
                isError = true,
                isAuthenticating = false,
                errorMessage = "Please Enter Correct Phone Number"
            )
            return
        }

        if (dob.isEmpty() || dob.length != 10) {
            _state.value = state.value.copy(
                isError = true,
                isAuthenticating = false,
                errorMessage = "Please Enter Correct dob"
            )
            return
        }

        try {

            val reqData = JSONObject()
            reqData.put("name", name)
            reqData.put("email", email)
            reqData.put("phone", phone)
            reqData.put("dob", dob)
            reqData.put("verifiedToken", verifiedToken)

            val response = client.post(Constant.createAccountRoute) {
                headers {
                    append("Content-Type", "application/json")
                }
                setBody(reqData.toString())
            }.body<String>()

            val respData = Gson().fromJson(response, AuthResponse::class.java)

            if (!respData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = respData.message,
                        isAuthenticating = false
                    )
                }
                return
            }

            val parseData = Gson().fromJson(respData.data, SignupResponse::class.java)
            authUseCases.setAuthData(parseData)
            _state.value = state.value.copy(
                isAuthenticating = false,
                isAuthenticated = true
            )
        } catch (err: Exception) {
            _state.value = state.value.copy(
                isError = true,
                isAuthenticating = false,
                errorMessage = "Please try after some time"
            )
            return
        }
    }


    fun clearMessage() {
        _state.update {
            state.value.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }

    fun setUnknownError() {
        _state.update {
            state.value.copy(
                isAuthenticating = false,
                isError = true,
                errorMessage = "Unknown Error"
            )
        }
    }
}