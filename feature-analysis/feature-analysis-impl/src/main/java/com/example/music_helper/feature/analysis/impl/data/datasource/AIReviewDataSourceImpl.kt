package com.example.music_helper.feature.analysis.impl.data.datasource

import android.util.Log
import com.example.music_helper.common.api.model.RemoteConfig
import com.example.music_helper.feature.analysis.api.data.datasource.AIReviewDataSource
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class AIReviewDataSourceImpl(private val remoteConfig: RemoteConfig) : AIReviewDataSource {

    override suspend fun get(prompt: String): String {
        try {
            Log.d("AIReviewDataSource", "HELLO")
            val model = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel(remoteConfig.geAiModel())
            val response =
                model.generateContent(prompt).text?.normalize() ?: DEFAULT_REVIEW
            Log.d("AIReviewDataSource", response)
            return response
        }
        catch (e: Exception) {
            Log.e("AI MODEL GETTING ERROR", "error: ${e.message}")
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
