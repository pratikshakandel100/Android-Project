package com.pratiksha.rojgarihub.presentation.auth.register

import com.pratiksha.rojgarihub.presentation.auth.UserType

sealed interface RegisterAction {
    data class RegisterAsChanged(val value: UserType) : RegisterAction
    data class FirstNameChanged(val value: String) : RegisterAction
    data class LastNameChanged(val value: String) : RegisterAction
    data class EmailChanged(val value: String) : RegisterAction
    data class PhoneChanged(val value: String) : RegisterAction
    data class CompanyNameChanged(val value: String) : RegisterAction
    data class PasswordChanged(val value: String) : RegisterAction
    data class ConfirmPasswordChanged(val value: String) : RegisterAction
    object TogglePasswordVisibility : RegisterAction
    object Submit : RegisterAction
    object  LoginClick: RegisterAction
}
