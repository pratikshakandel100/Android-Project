package com.pratiksha.rojgarihub.presentation.auth.login

import com.pratiksha.rojgarihub.presentation.auth.UserType

sealed interface LoginAction {
    data class LoginAsChanged(val value: UserType) : LoginAction
    data class EmailChanged(val value: String) : LoginAction
    data class PasswordChanged(val value: String) : LoginAction
    object TogglePasswordVisibility : LoginAction
    object ToggleRememberMe : LoginAction
    object SignInClicked : LoginAction
    object RegisterClicked : LoginAction
    object ForgotPasswordClicked : LoginAction
}
