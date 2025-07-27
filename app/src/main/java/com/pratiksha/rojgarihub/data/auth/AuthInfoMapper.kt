package com.pratiksha.rojgarihub.data.auth

import com.pratiksha.rojgarihub.domain.auth.AuthInfo


fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        userId = userId
    )
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        userId = userId
    )
}