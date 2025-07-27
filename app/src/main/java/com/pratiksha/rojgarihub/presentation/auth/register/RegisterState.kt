package com.pratiksha.rojgarihub.presentation.auth.register

data class RegisterState(
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
