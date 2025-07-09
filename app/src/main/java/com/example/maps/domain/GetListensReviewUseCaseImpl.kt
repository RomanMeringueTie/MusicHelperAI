package com.example.maps.domain

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class GetListensReviewUseCaseImpl() : GetListensReviewUseCase {
    override suspend fun invoke(listens: String): Result<String> {
        try {
            val model =
                Firebase.ai(backend = GenerativeBackend.googleAI())
                    .generativeModel("gemini-2.0-flash")
            val prompt =
                "Проанализируй список песен, которые прослушал пользователь $listens" +
                        "обращаясь к пользвателю на Вы, напиши следующие пункты:" +
                        "1. Настроение, 2. Черты характера, 3. Музыкальный вкус, 4. Рекомендации"
            val response = model.generateContent(prompt)
            return Result.success(response.text?.replace("*", "")?.replace("\\s+", "") ?: "-")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}