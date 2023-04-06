package com.example.core.data.usecases

import com.example.core.domain.repo.GameSettingsRepository

class getMnemoTypePrediction(val repo: GameSettingsRepository) {
    suspend fun execute() = repo.getAllLearningMethod().last().mnemoType
}