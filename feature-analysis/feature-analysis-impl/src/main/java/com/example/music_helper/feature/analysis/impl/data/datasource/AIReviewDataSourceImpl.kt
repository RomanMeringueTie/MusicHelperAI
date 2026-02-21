package com.example.music_helper.feature.analysis.impl.data.datasource

import com.example.music_helper.feature.analysis.api.data.datasource.AIReviewDataSource
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class AIReviewDataSourceImpl : AIReviewDataSource {
    override suspend fun get(prompt: String): String {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(GENERATIVE_MODEL)

        val response =
            model.generateContent(prompt).text?.normalize() ?: DEFAULT_REVIEW

        return response
    }

    // TODO (Optimize regexp (string object allocations))
    private fun String.normalize(): String {
        return this.replace("*", "")
            .replace(Regex(MARKDOWN_SYMBOL_PATTERN), "")
            .replace(Regex(WHITESPACE_PATTERN), " ")
    }

    private companion object {
        const val GENERATIVE_MODEL = "gemini-2.0-flash"

        const val MARKDOWN_SYMBOL_PATTERN = "(?<=[\\n\\t]) +"
        const val WHITESPACE_PATTERN = " +"
        const val DEFAULT_REVIEW = "-"
    }
}
