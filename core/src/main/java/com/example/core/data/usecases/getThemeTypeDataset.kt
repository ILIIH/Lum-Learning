package com.example.core.data.usecases

import com.example.core.domain.models.toDoubleArr
import com.example.core.domain.models.toMnemoTypeArr
import com.example.core.domain.models.toThemeTypeArr
import com.example.core.domain.repo.GameSettingsRepository

class getThemeTypeDataset(val repo: GameSettingsRepository) {
    suspend fun execute(): List<Pair<DoubleArray, DoubleArray>> {
        var result = ArrayList<Pair<DoubleArray, DoubleArray>>(50)
        repo.getAllGameResult().forEach { game ->
            val method = repo.getLearningMethodById(game.learningMethodId)
            result.add(
                Pair(
                    method.toThemeTypeArr(),
                    game.toDoubleArr()
                )
            )
        }
        return result
    }
}