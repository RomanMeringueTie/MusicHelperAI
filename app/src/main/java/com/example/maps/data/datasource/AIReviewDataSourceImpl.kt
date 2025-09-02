package com.example.maps.data.datasource

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class AIReviewDataSourceImpl : AIReviewDataSource {
    override suspend fun get(prompt: String): String {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.0-flash")

        val response =
            model.generateContent(prompt).text?.replace("*", "")
                ?.replace(Regex("(?<=[\\n\\t]) +"), "")
                ?.replace(Regex(" +"), " ") ?: "-"

        return response
    }
}