package com.pratiksha.rojgarihub.networking

import com.pratiksha.rojgarihub.domain.auth.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }


            defaultRequest {
                contentType(ContentType.Application.Json)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val authInfo = sessionStorage.getAuthInto()
                        val bearerTokens = BearerTokens(
                            accessToken = authInfo?.accessToken ?: "",
                            refreshToken = ""
                        )
                        println(" accessToken: ${bearerTokens.accessToken}")
                        Timber.tag("MyTag").d(" accessToken: ${bearerTokens.accessToken}")
                        bearerTokens
                    }
                }
            }
        }
    }
}