package com.example.music_helper.data.datasource

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class AIReviewDataSourceImpl : AIReviewDataSource {
    override suspend fun get(prompt: String): String {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.0-flash")

        val response =
            model.generateContent(prompt).text?.normalize() ?: "-"

        return response
    }

    private fun String.normalize(): String {
        return this.replace("*", "")
            .replace(Regex("(?<=[\\n\\t]) +"), "")
            .replace(Regex(" +"), " ")
    }
}