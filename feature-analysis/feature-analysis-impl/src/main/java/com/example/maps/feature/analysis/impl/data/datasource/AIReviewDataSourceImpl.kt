package com.example.maps.feature.analysis.impl.data.datasource

import com.example.maps.common.api.model.RemoteConfig
import com.example.maps.feature.analysis.api.data.datasource.AIReviewDataSource
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class AIReviewDataSourceImpl(private val remoteConfig: RemoteConfig) : AIReviewDataSource {

    override suspend fun get(prompt: String): String {
        try {
            val model = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel(remoteConfig.geAiModel())
            val response =
                model.generateContent(prompt).text?.normalize() ?: DEFAULT_REVIEW
            return response
        }
        catch (e: Exception) {
            return DEFAULT_REVIEW
        }
    }

    private fun String.normalize(): String {
        val normalizeRegex = Regex("[*$MARKDOWN_SYMBOL_PATTERN]|$WHITESPACE_PATTERN")
        return replace(normalizeRegex) { matchResult ->
            if (matchResult.value.matches(Regex(WHITESPACE_PATTERN))) " " else ""
        }
    }

    private companion object {
        const val MARKDOWN_SYMBOL_PATTERN = "(?<=[\\n\\t]) +"
        const val WHITESPACE_PATTERN = " +"
        const val DEFAULT_REVIEW = "-"
    }
}
