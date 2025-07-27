package com.pratiksha.rojgarihub.presentation.auth.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratiksha.rojgarihub.domain.auth.AuthRepository
import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.ui.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.pratiksha.rojgarihub.ui.asUiText
import com.pratiksha.rojgarihub.R
import com.pratiksha.rojgarihub.domain.util.Result
import timber.log.Timber

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private var _eventChannel = Channel<RegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(event: RegisterAction) {
        when (event) {
            is RegisterAction.FirstNameChanged -> state = state.copy(firstName = event.value)
            is RegisterAction.LastNameChanged -> state = state.copy(lastName = event.value)
            is RegisterAction.EmailChanged -> state = state.copy(email = event.value)
            is RegisterAction.PhoneChanged -> state = state.copy(phone = event.value)
            is RegisterAction.CompanyNameChanged -> state = state.copy(companyName = event.value)
            is RegisterAction.PasswordChanged -> state = state.copy(password = event.value)
            is RegisterAction.ConfirmPasswordChanged -> state =
                state.copy(confirmPassword = event.value)

            RegisterAction.TogglePasswordVisibility -> state =
                state.copy(isPasswordVisible = !state.isPasswordVisible)

            RegisterAction.Submit -> {
                validateAndRegister()
            }
           else -> Unit
        }
    }

    private fun validateAndRegister() {
        if (state.firstName.isBlank() || state.lastName.isBlank() || state.email.isBlank() || state.phone.isBlank() ||
            state.companyName.isBlank() || state.password.isBlank() || state.confirmPassword.isBlank()
        ) {
            state = state.copy(errorMessage = "All fields are required.")
            return
        }

        if (state.password != state.confirmPassword) {
            state = state.copy(errorMessage = "Passwords do not match.")
            return
        }

        register(state.email.trim(),state.password,state.firstName.trim(),state.lastName.trim(),state.phone.trim(),state.companyName.trim())

    }

    private fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        companyName: String
    ) {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = repository.register(
                email = email,
                password = password,
                firstName =firstName ,
                lastName =lastName ,
                phone =phone ,
                companyName =companyName
            )
            state = state.copy(isRegistering = false)
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        _eventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_email_exist)))
                    } else {
                        _eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}
