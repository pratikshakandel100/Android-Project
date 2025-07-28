package com.pratiksha.rojgarihub.data.auth

import com.pratiksha.rojgarihub.constant.ApiEndpoints
import com.pratiksha.rojgarihub.domain.auth.AuthInfo
import com.pratiksha.rojgarihub.domain.auth.AuthRepository
import com.pratiksha.rojgarihub.domain.auth.SessionStorage
import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.domain.util.asEmptyDataResult
import com.pratiksha.rojgarihub.networking.post
import io.ktor.client.HttpClient

class AuthRepoImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {
    override suspend fun loginEmployer(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = ApiEndpoints.EMPLOYER_LOGIN_ENDPOINT,
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.setAuthInfo(
                AuthInfo(
                    accessToken = result.data.token,
                    userId = result.data.userId,
                    userType = result.data.userType
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun loginJobSeeker(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = ApiEndpoints.JOB_SEEKER_LOGIN_ENDPOINT,
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.setAuthInfo(
                AuthInfo(
                    accessToken = result.data.token,
                    userId = result.data.userId,
                    userType = result.data.userType
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun registerEmployer(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        companyName: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = ApiEndpoints.EMPLOYER_REGISTER_ENDPOINT,
            body = RegisterRequest(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                companyName = companyName,
                phone = phone,
                confirmPassword = password
            )
        )
    }

    override suspend fun registerJobSeeker(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = ApiEndpoints.JOB_SEEKER_REGISTER_ENDPOINT,
            body = RegisterRequest(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                confirmPassword = password,
                companyName = ""
            )
        )
    }

}