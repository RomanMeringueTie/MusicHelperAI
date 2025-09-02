package com.example.maps.domain

import com.example.maps.data.datasource.AIReviewDataSource
import com.example.maps.data.model.ListensReview

class GetListensReviewUseCaseImpl(private val aIReviewDataSource: AIReviewDataSource) :
    GetListensReviewUseCase {
    override suspend fun invoke(listens: String): Result<ListensReview> {
        try {
            val recommendationsPrompt =
                "Проанализируй список песен $listens, обращаясь на Вы напиши," +
                        "каких исполнителей ты бы прекомендовал человеку, слушающему данные композиции," +
                        "не пиши введение, сразу ответ, старайся разбавлять текст эмодзи"
            val result = aIReviewDataSource.get(recommendationsPrompt)
            val listensReview = ListensReview(recommendations = result)
            return Result.success(listensReview)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}