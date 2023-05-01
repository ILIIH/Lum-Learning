package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameRepository

class getPredictedMnemoType(val repo: GameRepository) {
    suspend fun execute(): learningMethod {
        val res = repo.getAllLearningMethod()
        return res.last()
    }
}
