package com.pratiksha.admin.rojgarihub

import com.pratiksha.rojgarihub.domain.auth.AuthInfo
import com.pratiksha.rojgarihub.domain.auth.SessionStorage

class FakeSessionStorage : SessionStorage {
    override suspend fun getAuthInto(): AuthInfo? {
        return null
    }

    override suspend fun setAuthInfo(authInfo: AuthInfo?) {

    }
}
