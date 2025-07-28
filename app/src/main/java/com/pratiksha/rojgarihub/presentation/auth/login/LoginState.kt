package com.pratiksha.rojgarihub.presentation.auth.login

import com.pratiksha.rojgarihub.presentation.auth.UserType

data class LoginState(
    val loginAs: UserType = UserType.EMPLOYER,
    val email: String = "pratiksha@gmail.com",
    val password: String = "123456",
    val isPasswordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)
