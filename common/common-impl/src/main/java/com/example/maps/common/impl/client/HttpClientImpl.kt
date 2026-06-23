package com.example.maps.common.impl.client

import android.util.Log
import com.example.maps.common.api.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass
import io.ktor.client.HttpClient as KtorClient

class HttpClientImpl(override val baseUrl: String) : HttpClient {
    private val client: KtorClient = KtorClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override suspend fun <T : Any> get(arguments: String, type: KClass<T>): T {
        val response = client.get(baseUrl + arguments)
        return response.body(TypeInfo(type))
    }

    override suspend fun <T : Any> post(
        arguments: String,
        body: Any,
        type: KClass<T>,
    ): T {
        val response = client.post(baseUrl + arguments) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        Log.d("POST_RESPONSE", "${baseUrl + arguments}, $body")
        Log.d("POST_RESPONSE_STATUS", response.status.value.toString())
        return response.body(TypeInfo(type))
    }
}
