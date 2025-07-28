package com.pratiksha.rojgarihub.presentation.auth.register

import com.pratiksha.rojgarihub.presentation.auth.UserType


data class RegisterState(
    val registerAs: UserType = UserType.EMPLOYER,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val companyName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
    val isRegistering: Boolean = false,

    // This canRegister field is used to enabled or disable our register button
    val canRegister: Boolean = false
)
