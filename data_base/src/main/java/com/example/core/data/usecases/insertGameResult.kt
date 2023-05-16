package com.example.core.data.usecases

import com.example.core.domain.models.gameResult
import com.example.core.domain.repo.GameRepository

class insertGameResult(val repo: GameRepository) {
    suspend fun execute(result: gameResult) {

        repo.insertGameResult(gameResult(
            K = result.K,
            D = result.D,
            Ch = result.Ch,
            T = result.T,
            Tw1 = result.Tw1,
            Tw2 = result.Tw2,
            CurrentDay = result.CurrentDay,
            learningMethodId = repo.getCurrentLearningMethodId(),
            themeId = result.themeId,
            date = result.date,
        ))
    }
}
