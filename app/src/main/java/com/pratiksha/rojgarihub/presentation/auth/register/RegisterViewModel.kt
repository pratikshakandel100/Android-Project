package com.pratiksha.rojgarihub.presentation.auth.register

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

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private var _eventChannel = Channel<RegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.RegisterAsChanged -> state = state.copy(registerAs = action.value)
            is RegisterAction.FirstNameChanged -> state = state.copy(firstName = action.value)
            is RegisterAction.LastNameChanged -> state = state.copy(lastName = action.value)
            is RegisterAction.EmailChanged -> state = state.copy(email = action.value)
            is RegisterAction.PhoneChanged -> state = state.copy(phone = action.value)
            is RegisterAction.CompanyNameChanged -> state = state.copy(companyName = action.value)
            is RegisterAction.PasswordChanged -> state = state.copy(password = action.value)
            is RegisterAction.ConfirmPasswordChanged -> state =
                state.copy(confirmPassword = action.value)

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
            state.password.isBlank() || state.confirmPassword.isBlank()
        ) {
            state = state.copy(errorMessage = "All fields are required.")
            return
        }
        if (state.registerAs== UserType.EMPLOYER && state.companyName.isBlank()){
            state = state.copy(errorMessage = "Company Name required.")
            return
        }

        if (state.password != state.confirmPassword) {
            state = state.copy(errorMessage = "Passwords do not match.")
            return
        }

        register(
            state.email.trim(),
            state.password,
            state.firstName.trim(),
            state.lastName.trim(),
            state.phone.trim(),
            state.companyName.trim()
        )

    }

    private fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        companyName: String
    ) {
        if (state.registerAs == UserType.JOB_SEEKER) {
            registerJobSeeker(email, password, firstName, lastName, phone)
        }

        if (state.registerAs == UserType.EMPLOYER) {
            registerEmployer(email, password, firstName, lastName, phone, companyName)
        }
    }

    private fun registerJobSeeker(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String
    ) {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = repository.registerJobSeeker(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                phone = phone
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

    private fun registerEmployer(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        companyName: String
    ) {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = repository.registerEmployer(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                companyName = companyName
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
