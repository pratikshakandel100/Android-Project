package com.pratiksha.rojgarihub.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratiksha.rojgarihub.R
import com.pratiksha.rojgarihub.domain.auth.AuthRepository
import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.presentation.auth.UserType
import com.pratiksha.rojgarihub.ui.UiText
import com.pratiksha.rojgarihub.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private var _eventChannel = Channel<LoginEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.LoginAsChanged -> state = state.copy(loginAs = action.value)

            is LoginAction.EmailChanged -> {
                state = state.copy(email = action.value)
            }

            is LoginAction.PasswordChanged -> {
                state = state.copy(password = action.value)
            }

            LoginAction.TogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            LoginAction.ToggleRememberMe -> {
                state = state.copy(rememberMe = !state.rememberMe)
            }

            LoginAction.SignInClicked -> login(state.email.trim(), state.password)
            else -> Unit
        }
    }

    private fun login(email: String, password: String) {
        if (state.loginAs == UserType.JOB_SEEKER) {
            loginJobSeeker(email, password)
        }
        if (state.loginAs == UserType.EMPLOYER) {
            loginEmployer(email, password)
        }
    }

    private fun loginJobSeeker(email: String, password: String) {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.loginJobSeeker(
                email = email,
                password = password
            )
            state = state.copy(isLoggingIn = false)
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _eventChannel.send(
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    } else {
                        _eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

    private fun loginEmployer(email: String, password: String) {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.loginEmployer(
                email = email,
                password = password
            )
            state = state.copy(isLoggingIn = false)
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _eventChannel.send(
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    } else {
                        _eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }

}