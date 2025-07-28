package com.pratiksha.rojgarihub.presentation.auth.login

import com.pratiksha.rojgarihub.presentation.auth.UserType

data class LoginState(
    val loginAs: UserType = UserType.JOB_SEEKER,
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)
