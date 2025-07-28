package com.pratiksha.rojgarihub.domain.auth

import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult

interface AuthRepository {
    suspend fun loginJobSeeker(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun registerJobSeeker(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
    ): EmptyResult<DataError.Network>
    suspend fun loginEmployer(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun registerEmployer(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        companyName: String
    ): EmptyResult<DataError.Network>
}
