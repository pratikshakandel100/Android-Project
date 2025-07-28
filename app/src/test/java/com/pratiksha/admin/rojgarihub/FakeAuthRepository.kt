package com.pratiksha.admin.rojgarihub

import com.pratiksha.rojgarihub.domain.auth.AuthRepository
import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult
import com.pratiksha.rojgarihub.domain.util.Result

class FakeAuthRepository : AuthRepository {

    private var shouldFail = false

    fun setShouldFail(value: Boolean) {
        shouldFail = value
    }

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        return if (shouldFail || email != "user@example.com" || password != "password123") {
            Result.Error(DataError.Network.UNKNOWN)
        } else {
            Result.Success(Unit)
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        companyName: String
    ): EmptyResult<DataError.Network> {
        return if (shouldFail) {
            Result.Error(DataError.Network.UNKNOWN)
        } else {
            Result.Success(Unit)
        }
    }
}
