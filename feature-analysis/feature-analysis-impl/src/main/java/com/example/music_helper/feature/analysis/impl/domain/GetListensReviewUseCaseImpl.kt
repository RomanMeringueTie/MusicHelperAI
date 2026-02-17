package com.example.music_helper.feature.analysis.impl.domain

import com.example.music_helper.feature.analysis.api.domain.GetListensReviewUseCase
import com.example.music_helper.feature.analysis.api.model.ListensReview
import com.example.music_helper.feature.analysis.impl.data.datasource.AIReviewDataSource

class GetListensReviewUseCaseImpl(private val aIReviewDataSource: AIReviewDataSource) :
    GetListensReviewUseCase {
    override suspend fun invoke(listens: String): Result<ListensReview> {
        try {
            val recommendationsPrompt = RECOMMENDATION_PROMPT.format(listens)
            val result = aIReviewDataSource.get(recommendationsPrompt)
            val listensReview = ListensReview(recommendations = result)
            return Result.success(listensReview)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private companion object {
        const val RECOMMENDATION_PROMPT = "Проанализируй список песен %s, обращаясь на Вы напиши," +
                "каких исполнителей ты бы прекомендовал человеку, слушающему данные композиции," +
                "не пиши введение, сразу ответ, старайся разбавлять текст эмодзи"
    }
}
