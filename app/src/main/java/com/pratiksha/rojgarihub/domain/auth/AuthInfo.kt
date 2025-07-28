package com.pratiksha.rojgarihub.domain.auth

data class AuthInfo (
    val accessToken: String,
    val userId: String,
    val userType:String
)