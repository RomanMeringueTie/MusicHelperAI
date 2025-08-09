package com.example.maps.data.datasource

import com.example.maps.data.model.Review
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class ListensReviewDataSourceImpl : ListensReviewDataSource {
    override suspend fun get(listens: String): Review {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.0-flash")

        val recommendationsPrompt =
            "Проанализируй список песен $listens, обращаясь на Вы напиши," +
                    "каких исполнителей ты бы прекомендовал человеку, слушающему данные композиции," +
                    "не пиши введение, сразу ответ, старайся разбавлять текст эмодзи"

        val recommendations =
            model.generateContent(recommendationsPrompt).text?.replace("*", "")
                ?.replace(Regex("(?<=[\\n\\t]) +"), "")
                ?.replace(Regex(" +"), " ") ?: "-"

        val review = Review(
            recommendations = recommendations
        )
        return review
    }
}