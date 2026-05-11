package com.example.music_helper.common.impl.client

import com.example.music_helper.common.api.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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
}
