package com.pratiksha.rojgarihub.presentation.auth.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)
