package com.pratiksha.rojgarihub.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val userId: String,
    val userType:String
)
