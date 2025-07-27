package com.pratiksha.rojgarihub.domain.auth

interface SessionStorage {
    suspend fun getAuthInto(): AuthInfo?
    suspend fun setAuthInfo(authInfo: AuthInfo?)
}