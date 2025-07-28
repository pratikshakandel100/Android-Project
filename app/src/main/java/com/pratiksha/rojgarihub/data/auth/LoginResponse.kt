package com.pratiksha.rojgarihub.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val userId: String,
    val userType:String
)
