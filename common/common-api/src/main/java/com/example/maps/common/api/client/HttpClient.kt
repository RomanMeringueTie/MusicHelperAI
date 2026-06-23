package com.example.maps.common.api.client

import kotlin.reflect.KClass

interface HttpClient {
    val baseUrl: String
    suspend fun <T : Any> get(arguments: String, type: KClass<T>): T
    suspend fun <T : Any> post(arguments: String, body: Any, type: KClass<T>): T
}
