package com.example.music_helper.common.api.client

import kotlin.reflect.KClass

interface HttpClient {
    val baseUrl: String
    suspend fun <T : Any> get(arguments: String, type: KClass<T>): T
}
