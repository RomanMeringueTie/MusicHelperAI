package com.example.maps.domain

import com.example.maps.data.model.Review
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class GetListensReviewUseCaseImpl() : GetListensReviewUseCase {
    override suspend fun invoke(listens: String): Result<Review> {
        try {
            val model = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-2.0-flash")

            val traitsPrompt =
                "Проанализируй список песен $listens, обращаясь на Вы напиши," +
                        "какими личными чертами обладает человек, слушающий данные композиции," +
                        "не пиши введение, сразу ответ, лимит - 50 слов"
            val tastePrompt =
                "Проанализируй список песен $listens, обращаясь на Вы напиши," +
                        "каким музыкальным вкусом обладает человек, слушающий данные композиции," +
                        "не пиши введение, сразу ответ, лимит - 50 слов"
            val recommendationsPrompt =
                "Проанализируй список песен $listens, обращаясь на Вы напиши," +
                        "каких исполнителей ты бы прекомендовал человеку, слушающему данные композиции," +
                        "не пиши введение, сразу ответ, лимит - 50 слов"

            val traits =
                model.generateContent(traitsPrompt).text?.replace("*", "")
                    ?.replace(Regex("(?<=[\\n\\t]) +"), "")
                    ?.replace(Regex(" +"), " ") ?: "-"
            val taste =
                model.generateContent(tastePrompt).text?.replace("*", "")
                    ?.replace(Regex("(?<=[\\n\\t]) +"), "")
                    ?.replace(Regex(" +"), " ") ?: "-"
            val recommendations =
                model.generateContent(recommendationsPrompt).text?.replace("*", "")
                    ?.replace(Regex("(?<=[\\n\\t]) +"), "")
                    ?.replace(Regex(" +"), " ") ?: "-"

            val review = Review(
                traits = traits,
                taste = taste,
                recommendations = recommendations
            )
            return Result.success(review)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}