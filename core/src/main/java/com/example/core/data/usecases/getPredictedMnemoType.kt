package com.example.core.data.usecases

import com.example.core.domain.models.learningMethod
import com.example.core.domain.repo.GameSettingsRepository

class getPredictedMnemoType(val repo: GameSettingsRepository) {
    suspend fun execute(): learningMethod? {
        val res = repo.getAllLearningMethod()
        return res.last()
    }
}
